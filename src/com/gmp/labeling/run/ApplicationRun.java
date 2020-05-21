package com.gmp.labeling.run;

import java.awt.EventQueue;
import com.gmp.labeling.userIOs.Login;

public class ApplicationRun {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login login = new Login();
					login.setVisible(true);
					login.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
