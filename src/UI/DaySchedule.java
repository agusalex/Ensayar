package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

 public class DaySchedule extends JFrame implements ActionListener{

 private JTable offers;
 private JButton offerInfo, daySchedule, auction, saveAndExit;
 private File userFolder, projectDirectory, dayOffers;
 private JMenu menuBar;
 private Integer width = 1024, height = width / 12*9;



 public DaySchedule(){

 JMenuBar menuBar_1 = new JMenuBar();
 menuBar_1.setToolTipText("jmenu");
 setJMenuBar(menuBar_1);

 try {
 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

 //esta es la carpeta con ofertas del proyecto
 projectDirectory = new File("Ofertas disponibles");
 if (!projectDirectory.exists()) {
 projectDirectory.mkdir();   //crea el directorio de archivos
 }

 } catch (Exception e) {}

 initialize();
 }


 /**
 * Initialize the contents of the frame.
 */
private void initialize() {
        new JFrame();
        setBounds(0, 0, width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        getContentPane().setLayout(null);



        JButton selectedOfferInfo = new JButton("info oferta ");
        selectedOfferInfo.setBounds(27, 607, 89, 23);
        getContentPane().add(selectedOfferInfo);


        JButton btnNewButton_1 = new JButton("info oferta");
        btnNewButton_1.setBounds(258, 607, 89, 23);
        getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("agregar oferta");
        btnNewButton_2.setBounds(527, 82, 143, 23);
        getContentPane().add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("cronograma del dia");
        btnNewButton_3.setBounds(527, 138, 143, 23);
        getContentPane().add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("cerrar ofertas");
        btnNewButton_4.setBounds(527, 225, 143, 23);
        getContentPane().add(btnNewButton_4);

        JButton btnNewButton_5 = new JButton("subastar");
        btnNewButton_5.setBounds(527, 315, 89, 23);
        getContentPane().add(btnNewButton_5);

        JButton btnNewButton_6 = new JButton("guardar y salir");
        btnNewButton_6.setBounds(527, 409, 89, 23);
        getContentPane().add(btnNewButton_6);

        TextArea textArea = new TextArea();
        textArea.setText("Info de la app");
        textArea.setBounds(622, 390, 380, 160);
        getContentPane().add(textArea);

        JList list = new JList();
        list.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
        list.setBounds(10, 26, 143, 493);
        getContentPane().add(list);

        JList list_1 = new JList();
        list_1.setBounds(199, 40, 151, 479);
        getContentPane().add(list_1);






}


@Override
public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

        }

public static void main(String [] args){
        DaySchedule day = new DaySchedule();
        }
        }

