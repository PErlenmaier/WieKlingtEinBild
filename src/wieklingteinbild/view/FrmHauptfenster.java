/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wieklingteinbild.view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import wieklingteinbild.controller.Check;
import wieklingteinbild.controller.PictureDirectory;
import static wieklingteinbild.controller.PictureDirectory.addPicturesFromDir;
import static wieklingteinbild.controller.PictureDirectory.updatePictureDir;
import wieklingteinbild.controller.SoundDirectory;
import wieklingteinbild.model.Picture;
import static wieklingteinbild.model.PictureListe.pictureListe;
import static wieklingteinbild.model.SoundListe.soundListe;

/**
 *
 * @author Philipp
 */
public class FrmHauptfenster extends javax.swing.JFrame {

    String advSettings1;
    String advSettings2;
    String advSettings3;
    String advSettings4 = "0";
    String advSetting5 = "0";
    private AudioClip audioClip;
    
    private String algoInfoMessage = "";
    
    private int selectedAlgorithmus = 0;

    private String beschreibungLR_SCAN = "Bild wird von Links nach Rechts spaltenweise durchgescannt. Jede Zeile steht fuer eine Frequenz. Je intensiver R ←-\n"
            + "GB vom Pixel desto hoeher die Amplitude der jeweiligen Frequenz. Pixel werden nur betrachtet, wenn der Durschnitt\n"
            + "von RGB > Aktivierungsschwelle ist.";
    private String beschreibungLR_SCAN_NO_THRESHOLD = "Bild wird von Links nach Rechts spaltenweise durchgescannt. Jede Zeile steht fuer eine Frequenz. Je intensiver\n"
            + "RGB vom Pixel desto hoeher die Amplitude der jeweiligen Frequenz. Keine Aktivierungsgrenze.";
    private String beschreibungUD_SCAN = "Bild wird von Oben nach Unten zweilenweise durchgescannt. Jede Spalte steht fuer eine Frequenz. Je intensiver RGB vom Pixel desto hoeher die Amplitude der jeweiligen Frequenz. Pixel werden nur betrachtet, wenn der\n"
            + "Durschnitt von RGB > Aktivierungsschwelle ist.";
    private String beschreibungUD_SCAN_NO_THRESHOLD = "Bild wird von Oben nach Unten zweilenweise durchgescannt. Jede Spalte steht fuer eine Frequenz. Je intensiver\n"
            + "RGB vom Pixel desto hoeher die Amplitude der jeweiligen Frequenz. Keine Aktivierungsschwelle.";
    private String beschreibungTRIPLET = "Bild wird immer um 3 Byte (Triplet) oder Vielfache durchgesprungen. Jede Farbe hat eine Eigenschaft bei der\n"
            + "Tonerzeugung Rot: Lautstaerke ; Gruen: Tonhoehe; Blau: Tondauer.";
    private String beschreibungTRIPLET_JMP = "Bild wird mit einem Offset abhaengig von der Farbe durchgesprungen Rot: Lautstarke, Gruen: Tonhoehe; Blau:\n"
            + "keine Funktion.";
    private String beschreibungUD_LR_SCAN = "Das Bild wird von oben nach unten und von links nach rechts gleichzeitig gescannt. Die Senkrechte zur X-Achse\n"
            + "und die Parallele erzeugen beide jeweils einen Ton. Jeder Ton wird abhaengig von jeweiligen Farbanteil erzeugt.\n"
            + "Rot bestimmt die Frequenz, Gruen bestimmt die Lautstaerke und der gemeinsame Blauanteil die Dauer der beiden\n"
            + "Frequenzen.";

    /**
     * Creates new form FrmHauptfenster
     */
    public FrmHauptfenster() {
        initComponents();
        addImagesName();
        addSoundName();
    }

    public void setPicture(int selectedRow) throws IOException {
        TableModel model = jTable_images.getModel();
        String imageName = model.getValueAt(selectedRow, 0).toString();
        System.out.println(imageName);
        Image picture = ImageIO.read(new File(PictureDirectory.getPictureDir() + imageName));
        Image newpic = picture.getScaledInstance(jLabel_image.getWidth(), jLabel_image.getHeight(), Image.SCALE_SMOOTH);
        jLabel_image.setIcon(new ImageIcon(newpic));
    }

    public int nextORpreviousPicture(int i) {
        int row = jTable_images.getSelectedRow();

        int totalRows = jTable_images.getModel().getRowCount();
        int maxRows = totalRows - 1;

        if (i == 0) {
            if (row < maxRows) {
                row = row + 1;
            } else {
                row = 0;
            }
        } else if (row == 0) {
            row = maxRows;
        } else {
            row = row - 1;
        }
        jTable_images.setRowSelectionInterval(row, row);

        return row;
    }

    public void addImagesName() {
        PictureDirectory picDir = new PictureDirectory();
        picDir.addPicturesFromDir();
        DefaultTableModel model = (DefaultTableModel) jTable_images.getModel();
        model.setColumnIdentifiers(new String[]{("Images")});

        Object[] row = new Object[1];
        for (int i = 0; i < pictureListe.size(); i++) {
            row[0] = pictureListe.get(i).getName();
            model.addRow(row);
        }
    }

    public void addSoundName() {
        SoundDirectory soundDir = new SoundDirectory();
        soundDir.addSoundsFromDir();
        DefaultTableModel model = (DefaultTableModel) jTable_sound.getModel();
        model.setColumnIdentifiers(new String[]{("Sounds")});

        Object[] row = new Object[1];
        for (int i = 0; i < soundListe.size(); i++) {
            row[0] = soundListe.get(i).getName();
            model.addRow(row);
        }
    }

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) jTable_images.getModel();
        model.setRowCount(0);
    }

    public void clearSoundTable() {
        DefaultTableModel model = (DefaultTableModel) jTable_sound.getModel();
        model.setRowCount(0);
    }

    public void generateSound() {

        int freq = jSliderFrequency.getValue();
        String stringFreq = Integer.toString(freq);

        int volume = jSliderVolume.getValue();
        String stringVolume = Integer.toString(volume);

        int samples = jSliderSamples.getValue();
        String stringSamples = Integer.toString(samples);
        
        int threshold = jSliderThresould.getValue();
        String stringThreshold = Integer.toString(threshold);
        
        int tripletJump = jSliderTripletJumpSize.getValue();
        String stringTripletJump = Integer.toString(tripletJump);

        String wavName = jTextFieldSoundName.getText();
        if (wavName.isEmpty()) {
            wavName = "Test.wav";
        }
         if (wavName.endsWith(".wav") == false)
        {
                wavName =wavName + ".wav";
        }

        int row = jTable_images.getSelectedRow();

        try {

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "start",
                    PictureDirectory.getPictureDir() + "\\WieKlingtEinBild.exe",
                    PictureDirectory.getPictureDir() + "\\" + jTable_images.getValueAt(row, 0).toString(),
                    PictureDirectory.getPictureDir() + wavName,
                    stringFreq,
                    stringVolume,
                    stringSamples,
                    Integer.toString(selectedAlgorithmus),
                    stringFreq, stringThreshold, stringTripletJump,
                    advSettings4);
            System.out.println(pb.redirectInput());
            final Process p =  pb.start();
            BufferedReader br =new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while((line=br.readLine())!=null)
            {
                System.out.println(line);
            }
            p.waitFor();
            clearSoundTable();
            soundListe.clear();
            addSoundName();

        } catch (IOException ex) {
            Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean generateButtonControl() {
        ListSelectionModel listSelectionModel = jTable_images.getSelectionModel();
        if (listSelectionModel.isSelectionEmpty()) {
            return false;
        }
        return true;
    }

    public boolean playButtonControl() {
        ListSelectionModel listSelectionModel = jTable_sound.getSelectionModel();
        if (listSelectionModel.isSelectionEmpty()) {
            return false;
        }
        return true;
    }

    public boolean setRadioButton() {
        if (jRadioButtonInvers.isSelected()) {
            jTextFieldPixelInvers.setText("Pixel invertiert");
            return true;
        } else {
            jTextFieldPixelInvers.setText("Pixel nicht invertiert");
            return false;
        }
    }
    
    public void setAlgorithmus(){
        algoInfoMessage ="";
        String selectedAlgo = jComboBoxAlgorithmus.getSelectedItem().toString();
        
        if(selectedAlgo.equals("LR_Scan")){
            selectedAlgorithmus = 0;
            algoInfoMessage = beschreibungLR_SCAN;
        }else if(selectedAlgo.equals("LR_Scan no threshold")){
            selectedAlgorithmus = 1;
            algoInfoMessage = beschreibungLR_SCAN_NO_THRESHOLD;
        }else if(selectedAlgo.equals("Triplet")){
            selectedAlgorithmus = 4;
            
            algoInfoMessage = beschreibungTRIPLET;
        }else if(selectedAlgo.equals("Triplet with Jump")){
            selectedAlgorithmus = 5;
            algoInfoMessage = beschreibungTRIPLET_JMP;
        }else if(selectedAlgo.equals("UD_LR_Scan")){
            selectedAlgorithmus = 6;
            algoInfoMessage = beschreibungUD_LR_SCAN;
        }else if(selectedAlgo.equals("UD_Scan")){
            selectedAlgorithmus = 2;
            
            algoInfoMessage = beschreibungUD_SCAN;
        }else if(selectedAlgo.equals("UD_Scan no threshold")){
            selectedAlgorithmus = 3;
            algoInfoMessage = beschreibungUD_SCAN_NO_THRESHOLD;
        }else{
            algoInfoMessage = "No Algo set";
            selectedAlgorithmus = 0;
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_images = new javax.swing.JTable();
        jLabel_image = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextFieldSoundName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabSliderValue = new javax.swing.JLabel();
        jSliderVolume = new javax.swing.JSlider();
        jLabFrequency = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSliderFrequency = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();
        jLabSamples = new javax.swing.JLabel();
        jSliderSamples = new javax.swing.JSlider();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jRadioButtonInvers = new javax.swing.JRadioButton();
        jTextFieldPixelInvers = new javax.swing.JTextField();
        jButtonGenerate = new javax.swing.JButton();
        jComboBoxAlgorithmus = new javax.swing.JComboBox<>();
        jLabelAlgorithmus = new javax.swing.JLabel();
        jButtonAlgoInfo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jSliderThresould = new javax.swing.JSlider();
        jLabelThreshold = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSliderTripletJumpSize = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelTripletJump = new javax.swing.JLabel();
        jButtonPrevious = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_sound = new javax.swing.JTable();
        jButtonPlay = new javax.swing.JButton();
        jButtonPause = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();
        AddPicture = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Das klingende Bild");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTable_images.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_images.setColumnSelectionAllowed(true);
        jTable_images.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_imagesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_images);
        jTable_images.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jTextFieldSoundName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Sounddatei Name"));

        jLabel3.setText("0");

        jLabel4.setText("5000");

        jLabSliderValue.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabSliderValue.setText("1000");

        jSliderVolume.setBackground(new java.awt.Color(255, 255, 255));
        jSliderVolume.setMaximum(5000);
        jSliderVolume.setMinorTickSpacing(10);
        jSliderVolume.setPaintTicks(true);
        jSliderVolume.setValue(1000);
        jSliderVolume.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Volume"));
        jSliderVolume.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSliderVolumeMouseMoved(evt);
            }
        });

        jLabFrequency.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabFrequency.setText("44100");

        jLabel7.setText("0");

        jSliderFrequency.setBackground(new java.awt.Color(255, 255, 255));
        jSliderFrequency.setMaximum(88200);
        jSliderFrequency.setMinorTickSpacing(10000);
        jSliderFrequency.setPaintTicks(true);
        jSliderFrequency.setValue(44100);
        jSliderFrequency.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Frequency"));
        jSliderFrequency.setExtent(100);
        jSliderFrequency.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSliderFrequencyMouseMoved(evt);
            }
        });

        jLabel8.setText("88200");

        jLabSamples.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabSamples.setText("128");

        jSliderSamples.setBackground(new java.awt.Color(255, 255, 255));
        jSliderSamples.setMaximum(2048);
        jSliderSamples.setMinimum(128);
        jSliderSamples.setMinorTickSpacing(200);
        jSliderSamples.setPaintTicks(true);
        jSliderSamples.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Samples"));
        jSliderSamples.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSliderSamplesMouseMoved(evt);
            }
        });

        jLabel9.setText("0");

        jLabel10.setText("2048");

        jRadioButtonInvers.setBackground(new java.awt.Color(255, 255, 255));
        jRadioButtonInvers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonInversActionPerformed(evt);
            }
        });

        jTextFieldPixelInvers.setText("Pixel nicht invertiert");
        jTextFieldPixelInvers.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonGenerate.setText("Generate");
        jButtonGenerate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerateActionPerformed(evt);
            }
        });

        jComboBoxAlgorithmus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LR_Scan", "LR_Scan no threshold", "UD_Scan", "UD_Scan no threshold", "Triplet", "Triplet with Jump", "UD_LR_Scan" }));
        jComboBoxAlgorithmus.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jComboBoxAlgorithmus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAlgorithmusActionPerformed(evt);
            }
        });

        jLabelAlgorithmus.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabelAlgorithmus.setText("Algorithmus");

        jButtonAlgoInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wieklingteinbild/icons/Fragezeichen_24.png"))); // NOI18N
        jButtonAlgoInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlgoInfoActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jSliderThresould.setBackground(new java.awt.Color(255, 255, 255));
        jSliderThresould.setForeground(new java.awt.Color(255, 255, 255));
        jSliderThresould.setMaximum(255);
        jSliderThresould.setPaintTicks(true);
        jSliderThresould.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Threshold"));
        jSliderThresould.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSliderThresouldMouseMoved(evt);
            }
        });

        jLabelThreshold.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabelThreshold.setText("50");

        jLabel2.setText("255");

        jLabel1.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSliderThresould, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelThreshold))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderThresould, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jSliderTripletJumpSize.setBackground(new java.awt.Color(255, 255, 255));
        jSliderTripletJumpSize.setForeground(new java.awt.Color(255, 255, 255));
        jSliderTripletJumpSize.setMaximum(255);
        jSliderTripletJumpSize.setPaintTicks(true);
        jSliderTripletJumpSize.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Triplet jump size"));
        jSliderTripletJumpSize.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSliderTripletJumpSizeMouseMoved(evt);
            }
        });

        jLabel5.setText("0");

        jLabel6.setText("255");

        jLabelTripletJump.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabelTripletJump.setText("50");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelTripletJump)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldSoundName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonGenerate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(179, 179, 179)
                                .addComponent(jLabel10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabSamples)))
                        .addGap(12, 12, 12))
                    .addComponent(jSliderFrequency, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSliderVolume, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSliderSamples, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jRadioButtonInvers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPixelInvers))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jComboBoxAlgorithmus, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonAlgoInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabelAlgorithmus))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(173, 173, 173)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(179, 179, 179)
                                .addComponent(jLabel4)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabFrequency)
                    .addComponent(jSliderTripletJumpSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(197, 197, 197)
                        .addComponent(jLabel6))
                    .addComponent(jLabSliderValue))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldSoundName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabelAlgorithmus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxAlgorithmus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAlgoInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabelTripletJump, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderTripletJumpSize, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(11, 11, 11)
                .addComponent(jLabSliderValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jLabFrequency)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPixelInvers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabSamples)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSliderSamples, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonInvers, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButtonGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButtonPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wieklingteinbild/icons/Previous-32.png"))); // NOI18N
        jButtonPrevious.setText("Previous");
        jButtonPrevious.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });

        jButtonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wieklingteinbild/icons/Next-32.png"))); // NOI18N
        jButtonNext.setText("Next");
        jButtonNext.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jTable_sound.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable_sound);

        jButtonPlay.setText("Play");
        jButtonPlay.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayActionPerformed(evt);
            }
        });

        jButtonPause.setText("Stop");
        jButtonPause.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPauseActionPerformed(evt);
            }
        });

        jMenuFile.setText("File");

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        AddPicture.setText("Add Picture");
        AddPicture.setActionCommand("AddPicture");
        AddPicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPictureActionPerformed(evt);
            }
        });
        jMenuFile.add(AddPicture);

        jMenuBar1.add(jMenuFile);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(jButtonPause, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel_image, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel_image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPause, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jTable_imagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_imagesMouseClicked
        try {
            setPicture(jTable_images.getSelectedRow());
        } catch (IOException ex) {
            Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable_imagesMouseClicked

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jSliderVolumeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderVolumeMouseMoved
        jLabSliderValue.setText(String.valueOf(jSliderVolume.getValue()));
    }//GEN-LAST:event_jSliderVolumeMouseMoved

    private void jSliderFrequencyMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderFrequencyMouseMoved
        jLabFrequency.setText(String.valueOf(jSliderFrequency.getValue()));
    }//GEN-LAST:event_jSliderFrequencyMouseMoved

    private void jSliderSamplesMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderSamplesMouseMoved
        jLabSamples.setText(String.valueOf(jSliderSamples.getValue()));
    }//GEN-LAST:event_jSliderSamplesMouseMoved

    private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlayActionPerformed
        final SwingWorker worker = new SwingWorker(){           //Multi-Threading for computationally intensive Action in generateSound()
            @Override
            protected Object doInBackground() throws Exception {
                if (playButtonControl() == true) {

                    TableModel model = jTable_sound.getModel();
                    String soundName = model.getValueAt(jTable_sound.getSelectedRow(), 0).toString();
                    File datei = new File(SoundDirectory.getSoundDir() + "\\" + soundName);
                    try {
                        audioClip = Applet.newAudioClip(datei.toURL());
                        audioClip.play();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a sound!", "Alert", 2);
                }
                
            return 0;
            
            }
         };
        
        worker.execute();
        
    }//GEN-LAST:event_jButtonPlayActionPerformed

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
       
        if (pictureListe.isEmpty()) {
            System.exit(0);
        } else {
            try {
                int i = nextORpreviousPicture(0);
                setPicture(i);
            } catch (IOException ex) {
                Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousActionPerformed
        int i = nextORpreviousPicture(1);
        try {
            setPicture(i);
        } catch (IOException ex) {
            Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonPreviousActionPerformed

    private void jRadioButtonInversActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonInversActionPerformed
        setRadioButton();
        if (setRadioButton() == true) {
            advSettings4 = "1";
        } else {
            advSettings4 = "0";
        }


    }//GEN-LAST:event_jRadioButtonInversActionPerformed

    private void jButtonGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerateActionPerformed
        
        final SwingWorker worker = new SwingWorker(){           //Multi-Threading for computationally intensive Action in generateSound()
            @Override
            protected Object doInBackground() throws Exception {
                 if (generateButtonControl() == true) {
                     if (jTextFieldSoundName.getText().equals("")) {
                         JOptionPane.showMessageDialog(null, "Please type in Soundname!", "Alert", 2);
                     } else {
                         setAlgorithmus();
                         generateSound();
                     }
                 } else {
                     JOptionPane.showMessageDialog(null, "Please select a picture!", "Alert", 2);
                 }
                 System.out.println(advSettings4);
                 System.out.println(selectedAlgorithmus);
                 return 0;
            }    

        };
        worker.execute();

    }//GEN-LAST:event_jButtonGenerateActionPerformed

    private void jButtonAlgoInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlgoInfoActionPerformed
        setAlgorithmus();
        JOptionPane.showMessageDialog(null, algoInfoMessage, jComboBoxAlgorithmus.getSelectedItem().toString(), 3);
    }//GEN-LAST:event_jButtonAlgoInfoActionPerformed

    private void jButtonPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPauseActionPerformed
        audioClip.stop();
    }//GEN-LAST:event_jButtonPauseActionPerformed

    private void jComboBoxAlgorithmusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAlgorithmusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAlgorithmusActionPerformed

    private void jSliderThresouldMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderThresouldMouseMoved
        // TODO add your handling code here:
        jLabelThreshold.setText(String.valueOf(jSliderThresould.getValue()));
    }//GEN-LAST:event_jSliderThresouldMouseMoved

    private void AddPictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPictureActionPerformed

        final SwingWorker worker = new SwingWorker(){           //Multi-Threading for computationally intensive Action in generateSound()
           @Override
           protected Object doInBackground() throws Exception {

                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                //filter the files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.bmp","bmp");
                file.addChoosableFileFilter(filter);
                int result = file.showSaveDialog(null);
                 //if the user click on save in Jfilechooser
                if(result == JFileChooser.APPROVE_OPTION){
                    try {
                        System.out.println("hallo");
                        File selectedFile = file.getSelectedFile();
                        File destinationFile = new File(selectedFile.getName());
                        Path source = selectedFile.toPath();
                        Path destination = destinationFile.toPath();
                        Files.copy(source, destination,StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                 //if the user click on save in Jfilechooser
                else if(result == JFileChooser.CANCEL_OPTION){
                    System.out.println("No File Select");
                }
            return 0;
            }
            @Override
            protected void done() {
                clearTable();
                pictureListe.clear();
                updatePictureDir();
               addImagesName();
                    
             }
        };
        worker.execute();
    }//GEN-LAST:event_AddPictureActionPerformed

    private void jSliderTripletJumpSizeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jSliderTripletJumpSizeMouseMoved

        jLabelTripletJump.setText(String.valueOf(jSliderTripletJumpSize.getValue()));
    }//GEN-LAST:event_jSliderTripletJumpSizeMouseMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmHauptfenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmHauptfenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmHauptfenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmHauptfenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmHauptfenster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AddPicture;
    private javax.swing.JButton jButtonAlgoInfo;
    private javax.swing.JButton jButtonGenerate;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPause;
    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButtonPrevious;
    private javax.swing.JComboBox<String> jComboBoxAlgorithmus;
    private javax.swing.JLabel jLabFrequency;
    private javax.swing.JLabel jLabSamples;
    private javax.swing.JLabel jLabSliderValue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAlgorithmus;
    private javax.swing.JLabel jLabelThreshold;
    private javax.swing.JLabel jLabelTripletJump;
    private javax.swing.JLabel jLabel_image;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButtonInvers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSliderFrequency;
    private javax.swing.JSlider jSliderSamples;
    private javax.swing.JSlider jSliderThresould;
    private javax.swing.JSlider jSliderTripletJumpSize;
    private javax.swing.JSlider jSliderVolume;
    private javax.swing.JTable jTable_images;
    private javax.swing.JTable jTable_sound;
    private javax.swing.JTextField jTextFieldPixelInvers;
    private javax.swing.JTextField jTextFieldSoundName;
    // End of variables declaration//GEN-END:variables
}
