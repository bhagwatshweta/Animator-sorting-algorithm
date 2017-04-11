import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class AnimationApplet extends Applet {
	public AnimationApplet() {
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 611835262642887899L;
	TextField input, output;
	Label label1, label2;
	Button b1;
	JLabel lbl;
	JTextField maxText, minText, noOfIntsText, freeRunIntvlTxt;
	Integer noOfInts, max, min, freeRunIntvl;
	int num, sum = 0;
	JButton randomNosGenBtn, freeRunBtn, nextStepBtn;

	private java.util.List<Integer> objectLst, sortedObjectLst;
	private java.util.List<JLabel> intLblLst;

	JPanel inlzPnl, cntrlPnl, dsplyPnl, boxPnl, barPnl, messagePnl;

	BubbleInt bubbleInt = new BubbleInt();
	QuickString quickInt = new QuickString();

	Integer fstCmpPos, sndCmpPos, fstSwpPos, secSwpPos;
	Integer delay = 2000;
	Integer totalDelay = 2000;
	private JRadioButton boxViewRadioButton, barViewRadioButton, bubbleSortRadioButton, quickSortRadioButton;

	Thread shortThread;
	private JButton stopBtn;
	boolean run = false;
	boolean pause = false;
	boolean freeRun = true;
	boolean suspended = false;
	List<String> sortingAction;
	JFXPanel fxPanel;
	
	Scene scene;
	boolean firstTime = true;
	NumberAxis lineYAxis;
	CategoryAxis lineXAxis;
	BarChart barChart;
	XYChart.Series<String, Number> bar1;
	String sortMethod = "QUICK";
	private JLabel messageLabel;

	ColorSpace linearRGB = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);
	Color grey120 = new Color(linearRGB, new float[] { 120f / 255, 120f / 255, 120f / 255 }, 1f);
	boolean isMessageSet = false;
	private JLabel statLabel;
	private Component horizontalStrut;
	Integer compareCount = 0, swapCount = 0;

	List<JLabel> lableList;


	public void init() {

		objectLst = new ArrayList<Integer>();
		sortedObjectLst = new ArrayList<Integer>();
		intLblLst = new ArrayList<JLabel>();
		lableList = new ArrayList<JLabel>();

		fxPanel = new JFXPanel();

		this.setLayout(new BorderLayout());
		this.setSize(1200, 500);
		this.add(getIntializationPanel(), BorderLayout.WEST);
		this.add(getMessagePanel(), BorderLayout.NORTH);

		horizontalStrut = Box.createHorizontalStrut(200);
		messagePnl.add(horizontalStrut);

		statLabel = new JLabel("Total Compare : 0  & Total Swap : 0");
		statLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		messagePnl.add(statLabel);
		this.add(getDisplayPanel(), BorderLayout.CENTER);
		this.add(getControlPanel(), BorderLayout.SOUTH);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});

	}

	private JPanel getMessagePanel() {
		messagePnl = new JPanel();
		FlowLayout flowLayout = (FlowLayout) messagePnl.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		messagePnl.setBorder(BorderFactory.createLineBorder(Color.black));
		messageLabel = new JLabel("Please click on Populate Randoms to Start Soting Application");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messagePnl.add(messageLabel);
		messagePnl.setBackground(grey120);
		return messagePnl;
	}

	private JPanel getIntializationPanel() {
		inlzPnl = new JPanel();
		inlzPnl.setLayout(new BoxLayout(inlzPnl, BoxLayout.Y_AXIS));
		inlzPnl.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel maxLabel = new JLabel("<html>  Max value <br> ( &gt; min )</html> ");
		maxLabel.setHorizontalAlignment(SwingConstants.CENTER);
		maxLabel.setPreferredSize(new Dimension(100, 50));
		maxLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel minLabel = new JLabel("<html>  Min value <br> ( &lt; max )</html> ");
		minLabel.setHorizontalAlignment(SwingConstants.CENTER);
		minLabel.setPreferredSize(new Dimension(100, 50));
		minLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		maxText = new JTextField("99999");
		maxText.setAlignmentX(Component.LEFT_ALIGNMENT);
		maxText.setHorizontalAlignment(SwingConstants.RIGHT);
		maxText.setColumns(4);
		maxText.setMaximumSize(maxText.getPreferredSize());
		minText = new JTextField("-99999");
		minText.setAlignmentX(Component.LEFT_ALIGNMENT);
		minText.setHorizontalAlignment(SwingConstants.RIGHT);
		minText.setColumns(4);
		minText.setMaximumSize(minText.getPreferredSize());

		JLabel noOfIntsLabel = new JLabel("<html> No of <br>  Integers <br> ( &gt; 0)</html> ");
		noOfIntsLabel.setAlignmentX(0.5f);
		noOfIntsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noOfIntsLabel.setPreferredSize(new Dimension(100, 50));

		noOfIntsText = new JTextField("10");
		noOfIntsText.setHorizontalAlignment(SwingConstants.RIGHT);
		noOfIntsText.setAlignmentX(Component.LEFT_ALIGNMENT);
		noOfIntsText.setColumns(2);
		noOfIntsText.setMaximumSize(noOfIntsText.getPreferredSize());

		inlzPnl.add(Box.createVerticalStrut(10));
		inlzPnl.add(maxLabel);
		inlzPnl.add(Box.createVerticalStrut(10));
		inlzPnl.add(maxText);
		inlzPnl.add(Box.createVerticalStrut(10));
		inlzPnl.add(minLabel);
		inlzPnl.add(Box.createVerticalStrut(10));
		inlzPnl.add(minText);
		inlzPnl.add(Box.createVerticalStrut(10));
		inlzPnl.add(noOfIntsLabel);
		inlzPnl.add(Box.createVerticalStrut(10));
		inlzPnl.add(noOfIntsText);
		inlzPnl.add(Box.createVerticalStrut(10));

		randomNosGenBtn = new JButton(new AbstractAction("<html>Populate <br> Randoms</html>") {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					System.out.println("Object while generation Randoms>>" + this);
					objectLst.removeAll(objectLst);
				

					try {
						max = Integer.parseInt(maxText.getText().replaceAll(" ", ""));
					
					} catch (Exception e1) {
						messageLabel.setText(
								"Please enter max value greater than min value and Click again on Populate Randoms to populate random numbers");
						isMessageSet = true;
						throw new IllegalArgumentException("max must be entered or greater than max");
					}

					try {
						min = Integer.parseInt(minText.getText().replaceAll(" ", ""));
						

					} catch (Exception e1) {
						messageLabel.setText(
								"Please enter min value less than max value and Click again on Populate Randoms to populate random numbers");
						isMessageSet = true;
						throw new IllegalArgumentException("min must be entered or less than max");
					}

					if (max <= min) {
						messageLabel.setText(
								"Please enter max value greater than min value and Click again on Populate Randoms to populate random numbers");
						isMessageSet = true;
						throw new IllegalArgumentException("max must be greater than min");
					}

					try {
						noOfInts = Integer.parseInt(noOfIntsText.getText().replaceAll(" ", ""));
						if (noOfInts <= 0) {
							messageLabel.setText(
									"Please enter noOfInts value greater than 0 and Click again on Populate Randoms to populate random numbers");
							isMessageSet = true;
							throw new IllegalArgumentException("noOfInts must be between 1 to 15");
						}
					} catch (Exception e1) {
						messageLabel.setText(
								"Please enter No of Integers value greater than 0 and Click again on Populate Randoms to populate random numbers");
						isMessageSet = true;
						throw new IllegalArgumentException("noOfInts must be greater than 0");
					}

					setYAxis();

					for (int i = 0; i < noOfInts; i++) {
						int randomNo = getRandomNumberInRange(min, max);
						objectLst.add(randomNo);

					}

					displayNos(objectLst, fstCmpPos, sndCmpPos, fstSwpPos, secSwpPos);
					freeRunBtn.setEnabled(true);
					messageLabel.setText("Random Numbers Populated. Click on Free Run to Start Sorting");
					statLabel.setText("Total Compare : " + compareCount + "  & Total Swap : " + swapCount);
				} catch (Exception e1) {
					if (isMessageSet == false) {
						messageLabel.setText("Some unexpected error occured. Please try again");
					}
					reintialize();
					throw new IllegalArgumentException("Some unexpected error occured");
				}
			}
		});

		randomNosGenBtn.setPreferredSize(new Dimension(100, 40));
		randomNosGenBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		inlzPnl.add(randomNosGenBtn);
		inlzPnl.add(Box.createVerticalStrut(10));

		quickSortRadioButton = new JRadioButton("Quick Sort");
		quickSortRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					System.out.println("quickSortRadioButton");
					sortMethod = "QUICK";

				}
			}
		});
		quickSortRadioButton.setSelected(true);
		inlzPnl.add(quickSortRadioButton);
		inlzPnl.add(Box.createVerticalStrut(10));

		bubbleSortRadioButton = new JRadioButton("Bubble Sort");
		bubbleSortRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					System.out.println("bubbleSortRadioButton");
					sortMethod = "BUBBLE";
				}
			}
		});
		inlzPnl.add(bubbleSortRadioButton);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(quickSortRadioButton);
		buttonGroup.add(bubbleSortRadioButton);

		inlzPnl.setBackground(grey120);
		return inlzPnl;
	}

	private JPanel getControlPanel() {
		cntrlPnl = new JPanel();
		cntrlPnl.setBorder(BorderFactory.createLineBorder(Color.black));

		freeRunIntvlTxt = new JTextField("1000");
		freeRunIntvlTxt.setHorizontalAlignment(SwingConstants.RIGHT);
		freeRunIntvlTxt.setMaximumSize(noOfIntsText.getPreferredSize());
		freeRunIntvlTxt.setColumns(5);

		JLabel freeRunIntvlLbl = new JLabel("<html>Free Run Interval<br>(In Milli Sec)</html>");
		freeRunIntvlLbl.setPreferredSize(new Dimension(100, 50));
		freeRunIntvlLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		cntrlPnl.add(freeRunIntvlLbl);

		cntrlPnl.add(Box.createHorizontalStrut(10));
		cntrlPnl.add(Box.createHorizontalStrut(10));
		cntrlPnl.add(freeRunIntvlTxt);

		freeRunBtn = new JButton(new AbstractAction("<html>Free Run</html>") {
			@Override
			public void actionPerformed(ActionEvent e) {
				// add Action
				try {

					stopBtn.setEnabled(true);
					randomNosGenBtn.setEnabled(false);
					bubbleSortRadioButton.setEnabled(false);
					quickSortRadioButton.setEnabled(false);

					if (freeRun == true && pause == false) {
						System.out.println("Object while free run>>" + this);
						System.out.println("start free run");
						
						bubbleInt = new BubbleInt();
						quickInt = new QuickString();
						run = true;
						shortThread = new Thread() {
							public void run() {
								sortedObjectLst.removeAll(sortedObjectLst);
								sortedObjectLst.addAll(objectLst);

								if (sortMethod != null && sortMethod.equals("QUICK")) {
									sortingAction = quickInt.doQuickSort(sortedObjectLst);
								} else if (sortMethod != null && sortMethod.equals("BUBBLE")) {
									sortingAction = bubbleInt.doBubbleSort(sortedObjectLst);
								}
								System.out.println(sortingAction);
								animateSort();

							}
						};
						suspended = false;
						shortThread.start();
						freeRunBtn.setText("<html>Pause</html>");
						freeRun = false;
						pause = true;
					} else if (pause == true) {
						System.out.println("pause presseed");
						// shortThread.suspend();
						suspended = true;
						freeRunBtn.setText("<html>Resume</html>");
						pause = false;
						nextStepBtn.setEnabled(true);
					} else if (pause == false) {
						System.out.println("Resume presseed");
						// shortThread.resume();
						suspended = false;
						synchronized (objectLst) {
							objectLst.notify();
						}
						freeRunBtn.setText("<html>Pause</html>");
						pause = true;
						nextStepBtn.setEnabled(false);
					}
				} catch (Exception e1) {
					messageLabel.setText("Some unexpected error occured. Please try again");
					reintialize();
					throw new IllegalArgumentException("Some unexpected error occured");
				}
			}
		});

		freeRunBtn.setPreferredSize(new Dimension(100, 40));
		freeRunBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		freeRunBtn.setEnabled(false);
		cntrlPnl.add(freeRunBtn);

		stopBtn = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// add Action
				run = false;
				reintialize();
			}

		});
		stopBtn.setText("<html>Stop</html>");
		stopBtn.setPreferredSize(new Dimension(100, 40));
		stopBtn.setAlignmentX(0.5f);
		stopBtn.setEnabled(false);
		cntrlPnl.add(stopBtn);

		boxViewRadioButton = new JRadioButton("Box View");
		boxViewRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					System.out.println("box view");
					// displayNos(objectLst, null, null, null, null);
					barPnl.setVisible(false);
					boxPnl.setVisible(true);
				}
			}
		});
		cntrlPnl.add(boxViewRadioButton);

		barViewRadioButton = new JRadioButton("Bar View");
		barViewRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					System.out.println("bar view");
					barPnl.setVisible(true);
					boxPnl.setVisible(false);
				}
			}
		});
		barViewRadioButton.setSelected(true);
		cntrlPnl.add(barViewRadioButton);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(boxViewRadioButton);
		buttonGroup.add(barViewRadioButton);


		nextStepBtn = new JButton(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// add Action
				try {
					suspended = false;
					synchronized (objectLst) {
						objectLst.notify();
					}

					Thread.sleep(freeRunIntvl / 2);
					
					suspended = true;
				} catch (Exception e1) {
					messageLabel.setText("Some unexpected error occured. Please try again");
					reintialize();
					throw new IllegalArgumentException("Some unexpected error occured");
				}
			}

		});
		nextStepBtn.setText("<html>Next</html>");
		nextStepBtn.setPreferredSize(new Dimension(100, 40));
		nextStepBtn.setAlignmentX(0.5f);
		nextStepBtn.setEnabled(false);
		cntrlPnl.add(nextStepBtn);

		cntrlPnl.setBackground(grey120);
		return cntrlPnl;
	}

	private void displayNos(java.util.List<Integer> intList, Integer fstCmpPos, Integer sndCmpPos, Integer fstSwpPos,
			Integer secSwpPos) {

		try {

			if (fstCmpPos == null && sndCmpPos == null && fstSwpPos == null && secSwpPos == null) {
				boxPnl.removeAll();
				boxPnl.revalidate();
				boxPnl.repaint();
				intLblLst.removeAll(intLblLst);

				int rows = 1;
				if (intList.size() > 10) {
					rows = (int) Math.ceil(intList.size() / 10);
				}
				boxPnl.setLayout(new GridLayout(rows, 0, 3, 3));

				Integer counter = 0;
				for (Integer intValue : intList) {


					JLabel intValueLabel = new JLabel(intValue.toString(), SwingConstants.CENTER);
					intValueLabel.setOpaque(true);
					
					intValueLabel.setBorder(BorderFactory.createLineBorder(Color.black));

					intValueLabel.setBackground(Color.ORANGE);
					if (counter.equals(fstCmpPos) || counter.equals(sndCmpPos)) {
						System.out.println("inside compare color value of counter>>" + counter + ">>value of fstCmpPos"
								+ fstCmpPos + ">>value of sndCmpPos>>" + sndCmpPos);

						intValueLabel.setBackground(Color.RED);

					} else if (counter.equals(fstSwpPos) || counter.equals(secSwpPos)) {
						System.out.println("inside swap color value of counter>>" + counter + ">>value of fstSwpPos"
								+ fstSwpPos + ">>value of sndCmpPos>>" + secSwpPos);

						intValueLabel.setBackground(Color.CYAN);
					}

					boxPnl.add(intValueLabel);
					intLblLst.add(intValueLabel);
					counter++;
					System.out.println("barCounter++-->" + counter);
				}
			} else {
				Integer counter = 0;
				Iterator<Integer> it1 = intList.iterator();
				Iterator<JLabel> it2 = intLblLst.iterator();
				while (it1.hasNext() && it2.hasNext()) {

					Integer intValue = it1.next();
					JLabel intValueLabel = it2.next();
		
					intValueLabel.setText(intValue.toString());
					
					if (counter.equals(fstCmpPos) || counter.equals(sndCmpPos)) {
						System.out.println("inside compare color value of counter>>" + counter + ">>value of fstCmpPos"
								+ fstCmpPos + ">>value of sndCmpPos>>" + sndCmpPos);

						intValueLabel.setBackground(Color.RED);

					} else if (counter.equals(fstSwpPos) || counter.equals(secSwpPos)) {
						System.out.println("inside swap color value of counter>>" + counter + ">>value of fstSwpPos"
								+ fstSwpPos + ">>value of sndCmpPos>>" + secSwpPos);

						intValueLabel.setBackground(Color.CYAN);
					} else {
						intValueLabel.setBackground(Color.ORANGE);
					}

					counter++;
					System.out.println("barCounter++-->" + counter);
				}
			}

			Platform.runLater(() -> {

			

				if (barChart != null) {
					barChart.getData().removeAll(barChart.getData());

					barChart.layout();
					bar1 = new XYChart.Series<>();
					Integer barCounter = 0;
					for (Integer intValue : intList) {
						Data data = getData(intValue, ordinal(barCounter+1) +" : " + intValue.toString());
						Node node = data.getNode();
						bar1.getData().add(data);
						barCounter++;
					}
					barChart.getData().addAll(bar1);

					barCounter = 0;
					for (Data d : bar1.getData()) {
						d.getNode().setStyle("-fx-bar-fill: orange;");
						if (barCounter.equals(fstCmpPos) || barCounter.equals(sndCmpPos)) {
							System.out.println("inside compare color value of counter>>" + barCounter
									+ ">>value of fstCmpPos" + fstCmpPos + ">>value of sndCmpPos>>" + sndCmpPos);

							d.getNode().setStyle("-fx-bar-fill: red;");
						} else if (barCounter.equals(fstSwpPos) || barCounter.equals(secSwpPos)) {
							System.out.println("inside swap color value of counter>>" + barCounter
									+ ">>value of fstSwpPos" + fstSwpPos + ">>value of sndCmpPos>>" + secSwpPos);

							d.getNode().setStyle("-fx-bar-fill: cyan;");
						}
						barCounter++;
						System.out.println("barCounter++-->" + barCounter);
					}

					
				}
				boxPnl.revalidate();
				boxPnl.repaint();

				barPnl.revalidate();
				barPnl.repaint();
			});
		} catch (Exception e1) {
			messageLabel.setText("Some unexpected error occured. Please try again");
			reintialize();
			throw new IllegalArgumentException("Some unexpected error occured");
		}
	}

	private JPanel getDisplayPanel() {

		dsplyPnl = new JPanel(new CardLayout());
		dsplyPnl.setDoubleBuffered(true);
		boxPnl = new JPanel();
		boxPnl.setDoubleBuffered(true);
	
		barPnl = new JPanel();
	
		dsplyPnl.add(boxPnl, "boxPnl");
		boxPnl.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		dsplyPnl.add(barPnl, "barPnl");
		dsplyPnl.setBorder(BorderFactory.createLineBorder(Color.black));
		dsplyPnl.setSize(1100, 400);
		barPnl.setVisible(false);
		boxPnl.setVisible(true);
		dsplyPnl.setBackground(Color.LIGHT_GRAY);
		return dsplyPnl;
	}

	

	private int getRandomNumberInRange(int min, int max) {
		int randomNo = 0;
		if (min >= max) {
			messageLabel.setText("Minimum Number should be less than Maximum Number");
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		randomNo = r.nextInt((max - min) + 1) + min;

		if (randomNo > max / 10 || randomNo <   min / 10) {
			return randomNo;
		} else {
			randomNo = getRandomNumberInRange(min, max);
		}

		return randomNo;
	}

	public void animateSort() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Run-->" + run);
			// check if boolean run is true?
			if (run == true) {
				try {
					if (freeRunIntvlTxt.getText() == null || freeRunIntvlTxt.getText().trim().equals("")) {
						freeRunIntvl = 0;
					} else {
						freeRunIntvl = Integer.parseInt(freeRunIntvlTxt.getText());
					}
				} catch (Exception e) {
					messageLabel.setText(
							"Please enter Free Run Interval value greater than or equal to 0 and Click again on Free Run to start Sorting");
					isMessageSet = true;
					reintializeDisplayPnl();
					throw new IllegalArgumentException("freeRunIntvl must be greater than 0");
				}

				for (String sortStr : sortingAction) {
					if (run == true) {
						System.out.println(sortStr);
						String[] sortStrArray = sortStr.split(",");
						String sortOpr = sortStrArray[0];
						String firstPos = sortStrArray[1];
						String secondPos = sortStrArray[2];
						Integer firstPosInt = Integer.parseInt(firstPos);
						Integer secondPosInt = Integer.parseInt(secondPos);
						if ("cm".equals(sortOpr)) {
							compareCount++;
							messageLabel.setText("Comparing " + ordinal(firstPosInt + 1) + " number( "
									+ objectLst.get(firstPosInt) + " )" + " to " + ordinal(secondPosInt + 1)
									+ " number( " + objectLst.get(secondPosInt) + " )");

							statLabel.setText("Total Compare : " + compareCount + "  & Total Swap : " + swapCount);

							displayNos(objectLst, firstPosInt, secondPosInt, null, null);
							sleepAndCheckForPauseAndStop();
						} else if ("sw".equals(sortOpr)) {
							swapCount++;
							messageLabel.setText("Swapping " + ordinal(firstPosInt + 1) + " number( "
									+ objectLst.get(firstPosInt) + " )" + " and " + ordinal(secondPosInt + 1)
									+ " number( " + objectLst.get(secondPosInt) + " )");
							
							displayNos(objectLst, null, null, firstPosInt, secondPosInt);
							sleepAndCheckForPauseAndStop();
							Collections.swap(objectLst, firstPosInt, secondPosInt);
							messageLabel.setText("Swapped " + ordinal(secondPosInt + 1) + " number( "
									+ objectLst.get(secondPosInt) + " )" + " and " + ordinal(firstPosInt + 1)
									+ " number( " + objectLst.get(firstPosInt) + " )");
							

							statLabel.setText("Total Compare : " + compareCount + "  & Total Swap : " + swapCount);

							displayNos(objectLst, null, null, firstPosInt, secondPosInt);
							sleepAndCheckForPauseAndStop();

						}
					}
				}
				displayNos(objectLst, null, null, null, null);
			}
			if (run == true) {
				messageLabel.setText("Sorting finished. Click on Populate Randoms to Start New Sorting");
			}
			reintialize();
		} catch (Exception e1) {
			if (isMessageSet == false) {
				messageLabel.setText("Some unexpected error occured. Please try again");
			}
			reintialize();
			throw new IllegalArgumentException("Some unexpected error occured");
		}
	}

	void sleepAndCheckForPauseAndStop() {
		try {

			if (run == false) {
				messageLabel.setText("Sorting Stopped. Click on Populate Randoms to Start New Sorting");
				return;
			}

			Thread.sleep(freeRunIntvl);
			synchronized (objectLst) {
				while (suspended) {
					messageLabel.setText(
							"Sorting Paused. Click on Resume to Continue Sorting or Click on Next to See Next Step of Soring");
					System.out.println("in suspended mode");
					objectLst.wait();
				}
			}

			if (run == false) {
				messageLabel.setText("Sorting Stopped. Click on Populate Randoms to Start New Sorting");
				return;
			}
		} catch (Exception e1) {
			messageLabel.setText("Some unexpected error occured. Please try again");
			reintialize();
			throw new IllegalArgumentException("Some unexpected error occured");
		}
	}

	void reintialize() {
		randomNosGenBtn.setEnabled(true);
		bubbleSortRadioButton.setEnabled(true);
		quickSortRadioButton.setEnabled(true);
		freeRunBtn.setEnabled(false);
		compareCount = 0;
		swapCount = 0;
		freeRun = true;
		pause = false;
		suspended = false;
		stopBtn.setEnabled(false);
		nextStepBtn.setEnabled(false);
		synchronized (objectLst) {
			objectLst.notifyAll();
		}
		freeRunBtn.setText("<html>Free Run</html>");
	}

	void reintializeDisplayPnl() {

		freeRunBtn.setText("<html>Free Run</html>");
		freeRun = true;
		pause = false;
		suspended = false;
		stopBtn.setEnabled(false);
		nextStepBtn.setEnabled(false);

	}

	private XYChart.Data getData(double x, double y) {
		XYChart.Data data = new XYChart.Data<>();
		data.setXValue(x);
		data.setYValue(y);
		return data;
	}

	private XYChart.Data getData(double x, String y) {
		XYChart.Data data = new XYChart.Data<>();
		data.setYValue(x);
		data.setXValue(y);
		return data;
	}

	private void initFX(JFXPanel fxPanel) {
		// This method is invoked on the JavaFX thread
		Scene scene = createScene();
		fxPanel.setScene(scene);
		barPnl.add(fxPanel, BorderLayout.CENTER);
	}

	private Scene createScene() {
		// Group root = new Group();
		GridPane grid = new GridPane();
		Scene scene = new Scene(grid, barPnl.getWidth(), barPnl.getHeight());

		lineYAxis = new NumberAxis(-100000, 100000, 10000);
		lineXAxis = new CategoryAxis();
		//lineXAxis.
		barChart = new BarChart<>(lineXAxis, lineYAxis);
		barChart.setAnimated(false);
		barChart.setLegendVisible(false);
		
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(100);
		grid.getColumnConstraints().addAll(col1);
		grid.add(barChart, 0, 0);
		grid.setAlignment(Pos.CENTER);
		return (scene);
	}

	private void setYAxis() {
		
		Long lowerBound = (long) min;
		Long upperBound = (long) max;

		if(upperBound>=0 && upperBound<=10)
		{
			upperBound = (long) 10;
		}
		else if(upperBound <=0 && upperBound >=-10)
		{
			upperBound = (long) 0;
		}
		else if(upperBound > 10 )
		{
			String getFstDgt = upperBound.toString().substring(0, 1);			
			upperBound = (long) ((Integer.parseInt(getFstDgt) + 1) * Math.pow(10, upperBound.toString().length()-1));
		}
		else if(upperBound < -10 )
		{
			upperBound = (long) 0;
		}
				
		Long tickUnit = (long) upperBound / 10;
		
		if(lowerBound>=0 && lowerBound<=10)
		{
			lowerBound = (long) 0;
		}
		else if(lowerBound <=0 && lowerBound >=-10)
		{
			lowerBound = (long) -10;
		}
		else if(lowerBound > 10 )
		{
			lowerBound = (long) 0;
		}
		else if(lowerBound < -10 )
		{
			String getFstDgt = lowerBound.toString().substring(1, 2);			
			lowerBound = (long) ((Integer.parseInt(getFstDgt) + 1) * Math.pow(10, lowerBound.toString().length()-2) * (-1));
		}
		
		if ((-1) * lowerBound > upperBound) {
			tickUnit = (long) (-1) * lowerBound / 10;
		} else {
			tickUnit = (long) upperBound / 10;
		}

		lineYAxis.setLowerBound(lowerBound);
		lineYAxis.setUpperBound(upperBound);
		lineYAxis.setTickUnit(tickUnit);
	}

	public String ordinal(int i) {
		String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		switch (i % 100) {
		case 11:
		case 12:
		case 13:
			return i + "th";
		default:
			return i + sufixes[i % 10];

		}
	}
	
	

}