package net.yoedtos.lequotes.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.yoedtos.lequotes.entity.StatusValues;
import net.yoedtos.lequotes.exception.AppException;
import net.yoedtos.lequotes.exception.AudioException;
import net.yoedtos.lequotes.service.AudioService;
import net.yoedtos.lequotes.service.DayQuoteService;
import net.yoedtos.lequotes.utils.AudioUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ControlPanel.class);
	
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnPlay;
	private JButton btnStop;
	private JButton btnPause;
	private JButton btnResume;
	private JButton btnCapture;
	private JButton btnClear;
	private JSlider volume;
	private JTextPane text;
	private Status status;
	
	private boolean play, capture, pause = false;
	
	
	public ControlPanel(JTextPane text) {
		this.text = text;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		
		btnPrevious = new JButton(Images.PREVIOUS);
		btnPrevious.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				previous();
			}
		});
		buttons.add(btnPrevious);
		
		btnNext = new JButton(ImagesB.NEXT);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		buttons.add(btnNext);
		
		btnPlay = new JButton(Images.PLAY);
		btnPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					play();
				} catch (AudioException ex) {
					UiMessage.showMessage(ex.getMessage(), "LeQuotes - Error", true);
				}
			}
		});
		buttons.add(btnPlay);
		
		btnPause = new JButton(Images.PAUSE);
		btnPause.setVisible(false);
		btnPause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		
		buttons.add(btnPause);
		
		btnResume = new JButton(Images.PLAY);
		btnResume.setVisible(false);
		btnResume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resume();
			}
		});
		buttons.add(btnResume);
		
		btnStop = new JButton(Images.STOP);
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();	
			}
		});
		buttons.add(btnStop);
		
		btnCapture = new JButton(ImagesB.CAPTURE);
		btnCapture.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					capture();
				} catch (AudioException ex) {
					UiMessage.showMessage(ex.getMessage(), "LeQuotes - Error", true);
				}
			}
		});
		buttons.add(btnCapture);
		
		btnClear = new JButton(Images.CLEAR);
		btnClear.setEnabled(false);
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clear();

			}
		});
		buttons.add(btnClear);
		add(buttons);
		
		volume = new JSlider(JSlider.HORIZONTAL);
		volume.setPreferredSize(new Dimension(30, 40));
		volume.setMaximum(200);
		volume.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(5, 0, 5, 5), "Volume",
				TitledBorder.CENTER, TitledBorder.CENTER));
		volume.setValue(125);
		volume.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent event) {
			
				JSlider js = (JSlider) event.getSource();
				if(js.getValueIsAdjusting()) {
					float result = js.getValue();
					float gain = result / 100;
					LOGGER.debug("Slide value: " + result + " gain " + gain);
					setVolume(gain);
				}
			}
		});
		
		Container panel = new Container();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		status = new Status();
		panel.add(status);
		panel.add(Box.createRigidArea(new Dimension(20, 40)));
		panel.add(volume);
		add(panel);
		
		load();
	}
	// end constructor 
	
	AudioService audioService = new AudioService();
	DayQuoteService dayQuoteService = new DayQuoteService();
	
	private void setVolume(float value) {
		audioService.setVolume(value);
	}
	
	private void setPlaying(boolean state) {
		
		if(state) {
			btnStop.setEnabled(true);
			btnPause.setVisible(true);
			btnPlay.setVisible(false);
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			btnCapture.setEnabled(false);
			btnClear.setEnabled(false);
		} else {
			btnStop.setEnabled(false);
			btnPause.setVisible(false);
			btnPlay.setVisible(true);
			btnPrevious.setEnabled(true);
			btnNext.setEnabled(true);
			btnCapture.setEnabled(true);
		}		
	}
	
	private void load() {
		try {
			text.setText(dayQuoteService.load());
			text.setCaretPosition(0);
		} catch (AppException e) {
			UiMessage.showMessage(e.getMessage(), "LeQuotes - Information!", false);
		}
	}

	private void previous() {
		try {
			text.setText(dayQuoteService.previous());
			text.setCaretPosition(0);
		} catch (AppException e) {
			UiMessage.showMessage(e.getMessage(), "LeQuotes - Information!", false);
		}
	}
	
	private void next() {
		try {
			text.setText(dayQuoteService.next());
			text.setCaretPosition(0);
		} catch (AppException e) {
			UiMessage.showMessage(e.getMessage(), "LeQuotes - Information!", false);
		}
	}

	private void clear() {	
		btnClear.setEnabled(false);
		audioService.clear();
	}
	
	private void stop() {
		play = false;
		capture = false;
		pause = false;

		setPlaying(false);
	
		if(btnPause.isVisible()) {
			btnPause.setVisible(false);
		} else {
			btnResume.setVisible(false);
		}
		checkCaptured();
	}
	
	private void pause() {
		pause = true;
		btnPause.setVisible(false);
		btnResume.setVisible(true);
	}
	
	private void resume() {
		pause = false;
		btnPause.setVisible(true);
		btnResume.setVisible(false);
	}
	
	private void play() throws AudioException {
		
		final Object[] objects = audioService.initPlay();
		
		if(objects != null) {
			play = true;
			setPlaying(true);
			
			SwingWorker<Boolean, StatusValues> worker = new SwingWorker<Boolean, StatusValues>() {
				
				SourceDataLine sourceLine = (SourceDataLine) objects[0];
				AudioInputStream audioInputStream = (AudioInputStream) objects[1];
				
				@Override
				public Boolean doInBackground() {
					
	                byte[] tempBuffer = new byte[1024];
	                
					int cnt = 0;
					
					while (cnt != -1 && play) {
						
						if(pause) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								LOGGER.error(e.getMessage());
							}
						} else {
							try {
								cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length);
							
								if (cnt > 0) {
									sourceLine.write(tempBuffer, 0, cnt);
									long time = (sourceLine.getMicrosecondPosition()/1000)/1000;
									float level = AudioUtils.calculateLevel(tempBuffer);
									publish(new StatusValues(level, time));
								}
							} catch (IOException e) {
								LOGGER.error(e.getMessage());
								UiMessage.showMessage("Unknow error occur!", "LeQuotes - Terminated", true);
								System.exit(1);
							}
						}
						if(!play) {
							publish(new StatusValues(0, 0));
						}
					}
					return true;
				}
				
				@Override
			    public void done() {
					publish(new StatusValues(0, 0));
					audioService.dropPlay();
					setPlaying(false);
					checkCaptured();
				}
				
				@Override
				protected void process(List<StatusValues> chunks) {
					super.process(chunks);
					
					StatusValues values = chunks.get(chunks.size()-1);
					status.setFill(values.getLevel(), values.getTime());
				}
			};
			worker.execute();
		}
	}
	
	private void capture() throws AudioException {
		capture = true;
		setPlaying(true);
		btnCapture.setEnabled(false);
		
		final Object[] objects = audioService.initCapture();
	
		SwingWorker<Boolean, StatusValues> worker = new SwingWorker<Boolean, StatusValues>() {
			
			TargetDataLine targetLine = (TargetDataLine) objects[0];
			ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) objects[1];
			
			@Override
			protected Boolean doInBackground() {
					
				byte[] buffer = new byte[1024];
				
				while(capture) {
					if(pause) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							LOGGER.error(e.getMessage());
						}
					} else {
						int cnt = targetLine.read(buffer, 0, buffer.length);
							if (cnt > 0) {
								long time = (targetLine.getMicrosecondPosition()/1000)/1000;
								float level = AudioUtils.calculateLevel(buffer);
								byteArrayOutputStream.write(buffer, 0, cnt);
								publish(new StatusValues(level, time));
							}
						}
				}
				if(!capture) {
					publish(new StatusValues(0, 0));
				}
				return true;
			}
			
			@Override
			protected void done() {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage());
					UiMessage.showMessage("Unknow error occur!", "LeQuotes - Terminated", true);
					System.exit(1);
				}
				audioService.dropCapture(byteArrayOutputStream);
				
				setPlaying(false);
				btnClear.setEnabled(true);
			
			}

			@Override
			protected void process(List<StatusValues> chunks) {
				super.process(chunks);
				
				StatusValues values = chunks.get(chunks.size()-1);
				status.setFill(values.getLevel(), values.getTime());
			}
			
		};
		worker.execute();	
	}
	
	private void checkCaptured() {
		if(audioService.hasCaptured()) {
			btnClear.setEnabled(true);
		}
	}
	
	public DayQuoteService getDayQuoteService() {
		return dayQuoteService;
	}
}
