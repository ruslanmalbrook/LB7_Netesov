import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.*;
import java.util.List;

public class PhonesChooser extends JFrame {
	public PhonesChooser() {
		super("Devices arrangement");
		setSize(400, 400);
		setLocation(200,200);
		setLayout(new BorderLayout());

		createMenu();
		createToolbar();
		createListsPanel();
		createToolbarListeners();
		createButtonsListeners();
	}

    private void createMenu() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(getParent(), "Точно хотите выйти?", "Подтвердите выход",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                if (option == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        fileMenu.add(exitItem);
    }

    private JButton resetButton, saveButton;
    private void createToolbar() {
        saveButton = new JButton("Сохранить");
        resetButton = new JButton("Восстановить");
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout());
        toolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        toolbar.add(saveButton); toolbar.add(resetButton);
        add(toolbar, BorderLayout.NORTH);
    }

    private DefaultListModel<String> playersModel, teamModel;
	private JList<String> playersList, teamList;
	private JButton takeButton, returnButton, takeAllButton, returnAllButton;
	private JPanel createButtonsPanel() {

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		buttonsPanel.setLayout(new GridLayout(4, 0));
		takeButton = new JButton(">");
		takeButton.setToolTipText("Добавить выделенные девайсы");
		takeAllButton = new JButton(">>");
		takeAllButton.setToolTipText("Добавить все девайсы");
		returnButton = new JButton("<");
		returnButton.setToolTipText("Вернуть выделенные девайсы");
		returnAllButton = new JButton("<<");
		returnAllButton.setToolTipText("Вернуть все девайсы");
		buttonsPanel.add(takeButton);
		buttonsPanel.add(takeAllButton);
		buttonsPanel.add(returnButton);
		buttonsPanel.add(returnAllButton);
		
		return buttonsPanel;
	}

    private void createButtonsListeners() {

		takeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				List<String> selection = playersList.getSelectedValuesList();
				for (String player : selection) {
					teamModel.addElement(player);
				}
				for (String player : selection) {
					playersModel.removeElement(player);
				}
			}		
		});
		
		returnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				List<String> selection = teamList.getSelectedValuesList();
				for (String player : selection) {
					playersModel.addElement(player);
				}
				for (String player : selection) {
					teamModel.removeElement(player);
				}
			}			
		});
		
		takeAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Enumeration<String> elements = playersModel.elements();
				while (elements.hasMoreElements()) {
					String next = elements.nextElement();
					teamModel.addElement(next);
				}
				playersModel.removeAllElements();
			}
		});
		
		returnAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Enumeration<String> elements = teamModel.elements();
				while (elements.hasMoreElements()) {
					String next = elements.nextElement();
					playersModel.addElement(next);
				}
				teamModel.removeAllElements();
			}
		});
		
	}

	private void createToolbarListeners() {

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String all = "";
				Enumeration<String> elements = teamModel.elements();
				while (elements.hasMoreElements()) {
					all += elements.nextElement() + "\n";
				}
				JOptionPane.showMessageDialog(getParent(), all,
						"Выбраны следующие девайсы: ",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				teamModel.removeAllElements();
				playersModel.removeAllElements();
				for (String player : PhonesEnum.Devices) {
					playersModel.addElement(player);
				}
			}
		});
	}
    
	private void createListsPanel() {

		playersModel = new DefaultListModel<String>();
		for (String player : PhonesEnum.Devices) {
			playersModel.addElement(player);
		}		
		teamModel = new DefaultListModel<String>();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		playersList = new JList<String>(playersModel);
		playersList.setToolTipText("Доступные девайсы");
		playersList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teamList = new JList<String>(teamModel);
		teamList.setToolTipText("Выбранные девайсы");
		teamList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(playersList);
		panel.add(createButtonsPanel());
		panel.add(teamList);
		add(panel, BorderLayout.CENTER);
	}	
	
}
