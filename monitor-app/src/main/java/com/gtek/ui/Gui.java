package com.gtek.ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class Gui {

	private JFrame frame;
	private JLabel info_TotalDevices;
	private JLabel info_TotalDevicesDown;
	private JLabel info_State;
	private JLabel info_Runtime;
	private JLabel info_Percent;
	private JButton btn_Toggle;

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("AMIDOWN");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton btn_Toggle = new JButton("Stop");
		this.setButtonToggle(btn_Toggle);
		
		JLabel lbl_TotalDevices = new JLabel("Monitored Network Devices: ");
		JLabel info_TotalDevices = new JLabel("0");
		this.setTotalDevicesLabel(info_TotalDevices);
		
		JLabel lbl_TotalDevicesDown = new JLabel("Network Devices Down: ");
		JLabel info_TotalDevicesDown = new JLabel("0");
		this.setTotalDevicesDownLabel(info_TotalDevicesDown);
		
		JLabel lbl_State = new JLabel("");
		lbl_State.setHorizontalAlignment(SwingConstants.CENTER);
		this.setStateLabel(lbl_State);
		
		JLabel lbl_Runtime = new JLabel("Runtime: ");
		JLabel info_Runtime = new JLabel("0");
		this.setRuntimeLabel(info_Runtime);
		
		JLabel lbl_PercentUp = new JLabel("Network Up: ");
		JLabel info_PercentUp = new JLabel("0%");
		this.setPercentLabel(info_PercentUp);
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lbl_Runtime)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(info_Runtime, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(208)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lbl_State, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
								.addComponent(btn_Toggle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))))
					.addGap(204))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_TotalDevices)
						.addComponent(lbl_TotalDevicesDown)
						.addComponent(lbl_PercentUp))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(info_PercentUp)
							.addContainerGap(413, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(info_TotalDevices, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(info_TotalDevicesDown, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_PercentUp)
						.addComponent(info_PercentUp))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_TotalDevices)
						.addComponent(info_TotalDevices))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_TotalDevicesDown)
						.addComponent(info_TotalDevicesDown))
					.addPreferredGap(ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
					.addComponent(lbl_State, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Toggle, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(57)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_Runtime)
						.addComponent(info_Runtime))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}
	
	/**
	 *	Get the main GUI window.
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * Set the label for total devices.
	 * @param lbl
	 */
	public void setTotalDevicesLabel(JLabel lbl) {
		this.info_TotalDevices = lbl;
	}
	
	/**
	 * Get the label for total devices.
	 * @return JLabel
	 */
	public JLabel getTotalDevicesLabel() {
		return this.info_TotalDevices;
	}
	
	/**
	 * Set the label for total devices down.
	 * @param lbl
	 */
	public void setTotalDevicesDownLabel(JLabel lbl) {
		this.info_TotalDevicesDown = lbl;
	}
	
	/**
	 * Get the label for total devices down.
	 * @return JLabel
	 */
	public JLabel getTotalDevicesDownLabel() {
		return this.info_TotalDevicesDown;
	}
	
	/**
	 * Set the label for the current state.
	 * @param lbl
	 */
	public void setStateLabel(JLabel lbl) {
		this.info_State = lbl;
	}
	
	/**
	 * Get the label for the current state.
	 * @return JLabel
	 */
	public JLabel getStateLabel() {
		return this.info_State;
	}
	
	/**
	 * Set the label for the current running time.
	 * @param lbl
	 */
	public void setRuntimeLabel(JLabel lbl) {
		this.info_Runtime = lbl;
	}
	
	/**
	 * Get the label for the current running time.
	 * @return JLabel
	 */
	public JLabel getRuntimeLabel() {
		return this.info_Runtime;
	}
	
	/**
	 * Set the label for the percent network
	 * up.
	 * 
	 * @param lbl
	 */
	public void setPercentLabel(JLabel lbl) {
		this.info_Percent = lbl;
	}
	
	/**
	 * Get the label for the percent network
	 * up.
	 * 
	 * @return JLabel
	 */
	public JLabel getPercentLabel() {
		return this.info_Percent;
	}
	
	/**
	 * Set the button for toggling STOP / START.
	 * @param btn
	 */
	public void setButtonToggle(JButton btn) {
		this.btn_Toggle = btn;
	}
	
	/**
	 * Get the button for toggling STOP / START.
	 * @return JLabel
	 */
	public JButton getButtonToggle() {
		return this.btn_Toggle;
	}
}