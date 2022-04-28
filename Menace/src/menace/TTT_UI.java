package menace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;


public class TTT_UI implements ActionListener {
    protected JFrame gameFrame;
    private Players pc, human,currentPlayer,human2;
    private Tree_UI auto,menaceFirstPlay, menaceSecondPlay;
    public char x;
    public char o;
    private boolean isP2P;
    private boolean isTraining;
    private JPanel northPanel,centerPanel,startPanel;
    private String xFile, oFile;
    char[] currState;
    JButton[] bton = new JButton[9];
    JMenuItem[] themeStyle = new JMenuItem[4];
    JLabel heading = new JLabel();
    private int total;
    char[][] field;
    private int[] command;
    private String addr_resource;

    public TTT_UI() throws InterruptedException {

        new Game_Audio();
        addr_resource =System.getProperty("user.dir")+"/Menace/src/resources";
        this.xFile =addr_resource+"/x3.gif";
        this.oFile=addr_resource+"/o3.gif";
        gameFrame = new JFrame();
        startPanel = new JPanel();
        startPanel.setBackground(Color.BLACK);
        titleMenu();

    }
    private void titleMenu() throws InterruptedException {

        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //JFrame.DISPOSE_ON_CLOSE)
        gameFrame.pack();
        ImageIcon icon = new ImageIcon(addr_resource+"/icon.jpg");
        gameFrame.setIconImage(icon.getImage());
        gameFrame.add(startPanel);
        customizeGUI();
        showUI();
        Object[] options = {"vs Player",
                "vs Computer",
                "Quit Game"};
        int n = JOptionPane.showOptionDialog(gameFrame,//parent container of JOptionPane
                "Choose Game Mode",
                "New Game",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,//do not use a custom Icon
                options,//the titles of buttons
                null);//default button title
        gameFrame.add(getCenterPanel(), BorderLayout.CENTER);
        gameFrame.add(getNorthPanel(), BorderLayout.NORTH);
        if (n == JOptionPane.YES_OPTION) {
            this.startPlayerNewGame();
        } else if (n == JOptionPane.NO_OPTION){
            this.startAINewGame();
        }
        else this.exit();

    }
    private void startAINewGame() throws InterruptedException {

        this.isP2P = false;
        this.x = 'X';
        this.o = 'O';
        isTraining = true;
        pc = new Players("Menace",'X', true,false);
        human  = new Players("Player", 'O', false,false);
        menaceFirstPlay = new Tree_UI(new char[3][3], false, this);
        menaceSecondPlay = new Tree_UI(new char[3][3], true, this);
        menaceFirstPlay.createTree();
        menaceSecondPlay.createTree();
        field=new char[3][3];
        isTraining= false;
        this.getHumanWayUI();

    }
    private void startPlayerNewGame() throws InterruptedException {

        this.x = 'X';
        this.o = 'O';
        this.isP2P = true;
        human = new Players("Player 1",'X', false,false);
        human2  = new Players("Player 2", 'O', false,false);
        field=new char[3][3];
        this.runTicTacToe(human,human2);

    }
    public JPanel getCenterPanel() {
        this.centerPanel = new JPanel();
       // this.centerPanel.setSize(200, 200);
        this.centerPanel.setBackground(Color.black);
        this.centerPanel.setLayout(new GridLayout(3, 3, 20,20));
        for (int i = 0; i < 9; i++) {
            bton[i] = new JButton();// creating object for each button element of array
            bton[i].setBackground(Color.WHITE);
            bton[i].addActionListener(this);
            bton[i].setOpaque(false);
            this.centerPanel.add(bton[i]); // adding each button to the pannel for buttons
        }
        return this.centerPanel;
    }

    public JPanel getNorthPanel()
    {
        this.northPanel = new JPanel();
        northPanel.setBackground(Color.BLACK);
        heading.setForeground(Color.WHITE);
       // heading.setFont(new Font("Ink Free", Font.BOLD, 75));
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setText("Welcome");
        this.northPanel.add(heading);
        return this.northPanel;
    }
    /*
     * Misc. UI adjustments are implemented here
     */
    private void customizeGUI() {
        gameFrame.setSize(500, 500); // Width, height
        gameFrame.setTitle("Tic Tac Toe");
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Change Theme");
        for(int i = 0; i<4; i++)
        {
            themeStyle[i] = new JMenuItem();
            switch (i){
                case 0:
                    themeStyle[i].setText("Abstract Theme 1");
                    break;
                case 1:
                    themeStyle[i].setText("Abstract Theme 2");
                    break;
                case 2:
                    themeStyle[i].setText("Funky Animals");
                    break;
                case 3:
                    themeStyle[i].setText("Mecha Art");
                    break;
            }
            themeStyle[i].addActionListener(this);
            menu.add(themeStyle[i]);
        }
        menubar.add(menu);
        gameFrame.setJMenuBar(menubar);
    }
    /**
     * A convenience method that uses the Swing dispatch threat to show the UI.
     * This prevents concurrency problems during component initialization.
     */
    public void showUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameFrame.setVisible(true);
            }
        });
    }
    public boolean CheckIfWin(char[][] field, TTT_UI ttt) {
        boolean isOneWon =  (CheckifWin(new Players("Player 1",x, true,false), 0, 0, field) || CheckifWin(new Players("Player 1",x, true,false), 1, 1, field) || CheckifWin(new Players("Player 1",x, true,false), 2, 2, field));
        boolean isTwoWon =  (CheckifWin(new Players("Player 2",o, true,false), 0, 0, field) || CheckifWin(new Players("Player 2",o, true,false), 1, 1, field) || CheckifWin(new Players("Player 2",o, true,false), 2, 2, field));
        return isOneWon || isTwoWon;
    }

    public boolean CheckifWin(Players player, int x, int y, char[][] field) {
        /*
         * Check it the player who just played has won
         */
        if(WinCondition_Row(player,x,y, field) || WinCondition_Column(player,x,y, field) || WinCondition_Diag(player, field)) return true;
        else return false;
    }

    public boolean WinCondition_Row(Players player, int x, int y, char[][] field) {
        /*
         * Check if the player by the row condition
         */
        for(int i=0; i<=2; i++) {
            if(field[i][y] != player.getMark()) return false;
        }
        return true;
    }

    public boolean WinCondition_Column(Players player, int x, int y, char[][] field) {
        /*
         * Check if the player by the column condition. Almost same as row condition but written seperately for the sake of readability
         */
        for(int i=0; i<=2; i++) {
            if(field[x][i] != player.getMark()) return false;
        }
        return true;
    }

    public boolean WinCondition_Diag(Players player, char[][] field) {
        /*
         * Check if the player by the diagonal condition
         */
        if (field[1][1] == player.getMark()) {
            if(field[0][0]==player.getMark() && field[2][2] == player.getMark()) return true;
            if(field[0][2]==player.getMark() && field[2][0] == player.getMark()) return true;
        }
        return false;
    }
    /**
     * Shut down the application
     */
    public void exit() {
        gameFrame.dispose();
        System.exit(0);
    }

    /**
     * Method to Display the guide during the beginning of the Application as well as
     * when a user needs it( Help-->Guide). This Method is called when MyAppUI is instantiated and in the Help
     * action of MenuManager
     */
    public void getHumanWayUI() throws InterruptedException {
        int reply = JOptionPane.showConfirmDialog(gameFrame,"Do You Want to go First?", "Please Select", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
           // JOptionPane.showMessageDialog(frame, "Player Playing First");
            auto = menaceSecondPlay;
            heading.setText("player first");
            this.runTicTacToe(human,pc);
        } else {
            //JOptionPane.showMessageDialog(frame, "Computer Playing First");
            auto = menaceFirstPlay;
            heading.setText("Computer first");
            this.runTicTacToe(pc,human);
        }
    }

    public void playAgain() throws InterruptedException {
        int reply = JOptionPane.showConfirmDialog(gameFrame,"Do You Want to Play Again?", "Please Select", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            gameFrame.remove(centerPanel);
            gameFrame.remove(northPanel);
            this.titleMenu();

        } else {
            this.exit();
        }
    }
    public boolean getTotal(char[][] field) {
        boolean result = true;
        for(int i=0;i<field.length;i++) {
            for(int j=0;j<field[i].length;j++) {
                if(field[i][j]==0) return false;
            }
        }
        return result;
    }
    private void runTicTacToe(Players p1, Players p2) throws InterruptedException {
        /*
         * Start this game
         */
        //remaining = new boolean[9];
        //Arrays.fill(remaining, false);

        this.total=9;
        this.currState = new char[9];
        Arrays.fill(currState, '0');
        boolean gameEnds = false;
        if(!p1.isComputer()) {
            System.out.println("You will play first");
            drawBoard();
        }
        while(total>0) {
            gameEnds = RunThePlayer(p1);
            if(!p1.isComputer() || !p2.isComputer()) drawBoard();
            if(gameEnds) {
                this.playAgain();
                break;
            }


            gameEnds = RunThePlayer(p2);
            if(!p1.isComputer() || !p2.isComputer()) drawBoard();
            if(gameEnds) {
                this.playAgain();
                break;
            }

        }
        if (!gameEnds)
        {
            heading.setText("Game has Drawn. ");
            this.playAgain();
            System.out.println("The game drawed out. No one won");
        }
    }


    private boolean RunThePlayer(Players p) throws InterruptedException {
        /*
         * Get the command, mark it and check if the player won
         * @param p - The player
         * @param sc - The scanner context
         * @return if the move won the player the game
         */

        boolean mark = false;
        boolean gameEnds = false;
        while(!mark) {
            if (total> 0) {
                int[] co = getCommand(p);
                if(co==null) {
                    System.out.println("Null!!!!!");
                }
                mark = Mark(co[0], co[1], p);
                if(mark) {
                    //changeArray(p, co[0],co[1]);
                    if(!isP2P)
                    {
                        this.auto=this.auto.children[co[0]][co[1]];
                    }

                    gameEnds = CheckifWin(p,co[0],co[1],this.field);
                    if(gameEnds) {
                        //System.out.println("Game has ended. " + p.getName() + " with " + p.getMark() + " has won");
                        heading.setText("Game has ended. " + p.getName() + " with " + p.getMark() + " has won");
                    }
                }
            }
            else break;
        }
        return gameEnds;
    }
    private boolean Mark(int x, int y, Players player) {
        /*
         * Get the command for the mentioned player
         * @param x - The player who will be playing this turn
         * @param y - The scanner object
         * @param player - The player who has just marked
         * @return - integer array, 0th element is x co-ordinate, 1st element is y co-ordinate
         */
        if(x>2 || x<0 || y>2 || y<0) {
            System.out.println("Invalid input, try again");
            return false;
        }
        if (field[x][y] != this.x && field[x][y] != this.o) {
            field[x][y] = player.getMark();
        }
        else {
            //System.out.println("Already taken. Input again");
            return false;
        }
        //You now have one less available square to play on
        this.total--;
        return true;
    }
    private void drawBoard() {
        /*
         * Function to print the board. Only needed if human wants to see the status
         */
        for (int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                if(field[i][j] == 0) {
                    System.out.print(" ");
                }
                else System.out.print(field[i][j]);
                if(j!=2) System.out.print("|");
            }
            System.out.println();
            if(i!=2) System.out.println("-+-+-");
        }
        System.out.println();
        System.out.println();
    }
    private int[] getCommand(Players player) throws InterruptedException {
        /*
         * Get the command for the mentioned player
         * @param player - The player who will be playing this turn
         * @param sc - The scanner object incase a human is playing
         * @return - integer array, 0th element is x co-ordinate, 1st element is y co-ordinate
         */
       // int[] co;
        if(player.isComputer()) {
            heading.setText("Wait for Computer");
            if(this.isTraining) {
                int i=0,j=0;
                int rand = new Random().nextInt(9);
                i = rand/3;
                j = rand%3;
                return new int[] {i,j};
            }
            else {
                this.currentPlayer =player;
                menaceButtonAction(this.auto.getChildWithValue());
                return this.auto.getChildWithValue();
            }

        }
        else
            {
           heading.setText(player.getName()+"'s Turn");
           this.currentPlayer =player;
           synchronized (this)
           {
               this.wait();
           }
            return this.getPlayerCommand();
            }



        }



    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        TTT_UI ttt_ui = new TTT_UI();

    }
    public void menaceButtonAction(int[] command)
    {
        if(command[0] == 0)
        {
            if(command[1]== 0)
            {
                bton[0].doClick();
            }
            if(command[1]== 1)
            {
                bton[1].doClick();
            }
            if(command[1]== 2)
            {
                bton[2].doClick();
            }
        }
        if(command[0] == 1)
        {
            if(command[1]== 0)
            {
                bton[3].doClick();
            }
            if(command[1]== 1)
            {
                bton[4].doClick();
            }
            if(command[1]== 2)
            {
                bton[5].doClick();
            }
        }
        if(command[0] == 2)
        {
            if(command[1]== 0)
            {
                bton[6].doClick();
            }
            if(command[1]== 1)
            {
                bton[7].doClick();
            }
            if(command[1]== 2)
            {
                bton[8].doClick();
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == themeStyle[0])
        {
            //JOptionPane.showMessageDialog(gameFrame,"theme 0 is pressed");
            this.xFile =addr_resource+"/x4.gif";
            this.oFile=addr_resource+"/o4.gif";
        }
        if(e.getSource() == themeStyle[1])
        {
            //JOptionPane.showMessageDialog(gameFrame,"theme 1 is pressed");
            this.xFile =addr_resource+"/x3.gif";
            this.oFile=addr_resource+"/o3.gif";
        }
        if(e.getSource() == themeStyle[2])
        {
            //JOptionPane.showMessageDialog(gameFrame,"theme 2 is pressed");
            this.xFile =addr_resource+"/x2.gif";
            this.oFile=addr_resource+"/o2.gif";
        }
        if(e.getSource() == themeStyle[3])
        {
            //JOptionPane.showMessageDialog(gameFrame,"theme 3 is pressed");
            this.xFile =addr_resource+"/x.gif";
            this.oFile=addr_resource+"/o.gif";
        }
        synchronized (this) {
            if (e.getSource() == bton[0]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[0].setIcon(new ImageIcon(xFile));
                }
                else{
                   bton[0].setIcon(new ImageIcon(oFile));
                }
                int[] co = {0, 0};
                this.command = co;
                bton[0].removeActionListener(this);
                this.notify();

            }
            if (e.getSource() == bton[1]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[1].setIcon(new ImageIcon(xFile));
                }
                else{
                    bton[1].setIcon(new ImageIcon(oFile));
                }
                int[] co = {0, 1};
                this.command = co;
                bton[1].removeActionListener(this);
                this.notify();

            }
            if (e.getSource() == bton[2]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[2].setIcon(new ImageIcon(xFile));
                }
                else{
                    bton[2].setIcon(new ImageIcon(oFile));
                }
                int[] co = {0, 2};
                this.command = co;
                bton[2].removeActionListener(this);
                this.notify();

            }
            if (e.getSource() == bton[3]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[3].setIcon(new ImageIcon(xFile));
                }
                else{
                    bton[3].setIcon(new ImageIcon(oFile));
                }
                int[] co = {1, 0};
                this.command = co;
                bton[3].removeActionListener(this);
                this.notify();

            }
            if (e.getSource() == bton[4]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[4].setIcon(new ImageIcon(xFile));
                }
                else{
                    bton[4].setIcon(new ImageIcon(oFile));
                }
                int[] co = {1, 1};
                this.command = co;
                bton[4].removeActionListener(this);
                this.notify();

            }
            if (e.getSource() == bton[5]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[5].setIcon(new ImageIcon(xFile));
                }
                else{
                    bton[5].setIcon(new ImageIcon(oFile));
                }
                int[] co = {1, 2};
                this.command = co;
                bton[5].removeActionListener(this);
                this.notify();

            }
            if (e.getSource() == bton[6]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[6].setIcon(new ImageIcon(xFile));
                }
                else{
                    bton[6].setIcon(new ImageIcon(oFile));
                }
                int[] co = {2, 0};
                this.command = co;
                bton[6].removeActionListener(this);
                this.notify();

            }
            if (e.getSource() == bton[7]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[7].setIcon(new ImageIcon(xFile));
                }
                else{
                    bton[7].setIcon(new ImageIcon(oFile));
                }
                int[] co = {2, 1};
                this.command = co;
                bton[7].removeActionListener(this);
                this.notify();


            }
            if (e.getSource() == bton[8]) {
                if(currentPlayer.getMark() == 'X')
                {
                    bton[8].setIcon(new ImageIcon(xFile));
                }
                else{
                    bton[8].setIcon(new ImageIcon(oFile));
                }
                int[] co = {2, 2};
                this.command = co;
                bton[8].removeActionListener(this);
                this.notify();


            }
        }

    }

 public int[] getPlayerCommand(){
        return this.command;
 }


}
