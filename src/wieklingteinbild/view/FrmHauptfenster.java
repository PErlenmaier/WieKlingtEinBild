/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wieklingteinbild.view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.Popup;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import wieklingteinbild.controller.PictureDirectory;
import wieklingteinbild.controller.SoundDirectory;
import wieklingteinbild.model.Picture;
import static wieklingteinbild.model.PictureListe.pictureListe;
import static wieklingteinbild.model.SoundListe.soundListe;

/**
 *
 * @author Philipp // Max
 */
public class FrmHauptfenster extends javax.swing.JFrame {

    String advSettings1;
    String advSettings2;
    String advSettings3;
    String advSettings4 = "0";
    /**
     * Creates new form FrmHauptfenster
     */
    AtomicInteger Algorithmus = new AtomicInteger(0);
  
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

        String wavName = jTextFieldSoundName.getText();
        if (wavName.isEmpty()) {
            wavName = "Test.wav";
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
                    "0", "12000", "180", "5",
                    advSettings4);
            pb.start();

        } catch (IOException ex) {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_images = new javax.swing.JTable();
        jLabel_image = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldSoundName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabSliderValue = new javax.swing.JLabel();
        jSliderVolume = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabFrequency = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSliderFrequency = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();
        jLabSamples = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSliderSamples = new javax.swing.JSlider();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jToggleButtonGenerate = new javax.swing.JToggleButton();
        jButtonPlay = new javax.swing.JButton();
        AlgoComboBox = new javax.swing.JComboBox<>();
        jPanelGrundeinstellungen = new javax.swing.JLabel();
        jRadioButtonInvers = new javax.swing.JRadioButton();
        jTextFieldPixelInvers = new javax.swing.JTextField();
        jButtonGenerate = new javax.swing.JButton();
        jButtonPrevious = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_sound = new javax.swing.JTable();
        jButtonUpdateSoundListe = new javax.swing.JButton();
        jButtonPlay = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Das klingende Bild");

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

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Sound Dateiname");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setText("0");

        jLabel4.setText("100");

        jLabSliderValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabSliderValue.setText("50");

        jSliderVolume.setMinorTickSpacing(10);
        jSliderVolume.setPaintTicks(true);
        jSliderVolume.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSliderVolumeMouseMoved(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Volume");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Frequency");

        jLabFrequency.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabFrequency.setText("44100");

        jLabel7.setText("0");

        jSliderFrequency.setMaximum(88200);
        jSliderFrequency.setMinorTickSpacing(10000);
        jSliderFrequency.setPaintTicks(true);
        jSliderFrequency.setValue(44100);
        jSliderFrequency.setExtent(100);
        jSliderFrequency.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSliderFrequencyMouseMoved(evt);
            }
        });

        jLabel8.setText("88200");

        jLabSamples.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabSamples.setText("128");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Samples");

        jSliderSamples.setMaximum(2048);
        jSliderSamples.setMinimum(128);
        jSliderSamples.setMinorTickSpacing(200);
        jSliderSamples.setPaintTicks(true);
        jSliderSamples.setValue(128);
        jSliderSamples.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jSliderSamplesMouseMoved(evt);
            }
        });

        jLabel9.setText("0");

        jLabel10.setText("2048");

        jPanelGrundeinstellungen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanelGrundeinstellungen.setText("Grundeinstellungen");

        jRadioButtonInvers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonInversActionPerformed(evt);
            }
        });

        jTextFieldPixelInvers.setText("Pixel nicht invertiert");

        jButtonGenerate.setText("Generate");
        jButtonGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerateActionPerformed(evt);
            }
        });

        AlgoComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Links Rechts Scan", "Links Rechts Scan mit Aktivierungsschwelle", "Oben Unten Scan", "Oben Unten Scan mit Aktivierungsschwelle", "Triplet Triplet mit Sprung", "Unten Oben Links Rechts Scan" }));
        AlgoComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlgoComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jToggleButtonGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabSliderValue))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4))
                                .addComponent(jSliderVolume, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSliderFrequency, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabFrequency))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabSamples))
                                .addComponent(jSliderSamples, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(AlgoComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 271, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderFrequency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldSoundName)
                    .addComponent(jButtonGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSliderSamples, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabSamples))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabFrequency))
                    .addComponent(jSliderVolume, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jPanelGrundeinstellungen)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButtonInvers)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldPixelInvers, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 19, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabSliderValue)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AlgoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jTextFieldSoundName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanelGrundeinstellungen)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabSliderValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabFrequency))
                .addGap(18, 18, 18)
                .addComponent(jSliderFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabSamples))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderSamples, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioButtonInvers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldPixelInvers))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addComponent(jButtonGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButtonPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wieklingteinbild/icons/Previous-32.png"))); // NOI18N
        jButtonPrevious.setText("Previous");
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });

        jButtonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wieklingteinbild/icons/Next-32.png"))); // NOI18N
        jButtonNext.setText("Next");
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

        jButtonUpdateSoundListe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wieklingteinbild/icons/update_32.png"))); // NOI18N
        jButtonUpdateSoundListe.setText("Update");
        jButtonUpdateSoundListe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateSoundListeActionPerformed(evt);
            }
        });

        jButtonPlay.setText("Play");
        jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayActionPerformed(evt);
            }
        });

        jMenuFile.setText("File");
        jMenuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFileActionPerformed(evt);
            }
        });

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar1.add(jMenuFile);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_image, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                        .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonUpdateSoundListe, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_image, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_image, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonUpdateSoundListe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(38, 38, 38))
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
        //File datei = new File(PictureDirectory.getPictureDir() + "\\"+jTextField1.getText());
                TableModel model = jTable_sound.getModel();
        String soundName = model.getValueAt(jTable_sound.getSelectedRow(), 0).toString();
        File datei = new File(SoundDirectory.getSoundDir() + "\\"+soundName);
        try {
            AudioClip clip = Applet.newAudioClip(datei.toURL());
            clip.play();
        } catch (MalformedURLException ex) {
            Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonPlayActionPerformed

    private void jToggleButtonGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonGenerateActionPerformed
     
        int freq = jSliderFrequency.getValue();
        String stringFreq = Integer.toString(freq);
        
        int volume = jSliderVolume.getValue();
        String stringVolume = Integer.toString(volume);

        String wavName = jTextField1.getText();
        if (wavName.isEmpty()) {
            wavName = "Test.wav";
            
            Component frame = null;
            JOptionPane.showMessageDialog(frame,
            "Please enter a valid soundfile name (XYZ.wav)");
        }
        if(wavName.contains(".wav") == false)
        {
            wavName = wavName + ".wav";
        }

        int row = jTable_images.getSelectedRow();

        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "start",
                PictureDirectory.getPictureDir() + "\\WieKlingtEinBild.exe",
                PictureDirectory.getPictureDir() + "\\" + jTable_images.getValueAt(row, 0).toString(),
                PictureDirectory.getPictureDir() + wavName,
                stringFreq,
                stringVolume, "256", Algorithmus.toString(), "12000", "180", "5", "1");
            pb.start();

        } catch (IOException ex) {
            Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null,ex);
        }
        TableModel model = jTable_sound.getModel();
        //Added update of sound dir
        SoundDirectory.updateSoundDir();
    }//GEN-LAST:event_jToggleButtonGenerateActionPerformed
        if (playButtonControl() == true) {

            TableModel model = jTable_sound.getModel();
            String soundName = model.getValueAt(jTable_sound.getSelectedRow(), 0).toString();
            File datei = new File(SoundDirectory.getSoundDir() + "\\" + soundName);
            try {
                AudioClip clip = Applet.newAudioClip(datei.toURL());
                clip.play();
            } catch (MalformedURLException ex) {
                Logger.getLogger(FrmHauptfenster.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a sound!", "Alert", 2);
        }
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

    private void AlgoComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlgoComboBoxActionPerformed
        //Choose wich algorithmus is used
        switch(AlgoComboBox.getSelectedIndex())
        {
            case 0:     Algorithmus.getAndAdd(0);
                        break;
            case 1:     Algorithmus.getAndAdd(1);
                        break;
            case 2:     Algorithmus.getAndAdd(2);
                        break;
            case 3:     Algorithmus.getAndAdd(3);
                        break;
            case 4:     Algorithmus.getAndAdd(4);
                        break;
            case 5:     Algorithmus.getAndAdd(5);
                        break;
            case 6:     Algorithmus.getAndAdd(6);
                        break;
            default:    Algorithmus.getAndAdd(7);
                        break;
             
        }
    }//GEN-LAST:event_AlgoComboBoxActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jMenuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuFileActionPerformed
    private void jButtonUpdateSoundListeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateSoundListeActionPerformed
        clearSoundTable();
        soundListe.clear();
        addSoundName();
    }//GEN-LAST:event_jButtonUpdateSoundListeActionPerformed

    private void jRadioButtonInversActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonInversActionPerformed
        setRadioButton();
        if (setRadioButton() == true) {
            advSettings4 = "1";
        } else {
            advSettings4 = "0";
        }


    }//GEN-LAST:event_jRadioButtonInversActionPerformed

    private void jButtonGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerateActionPerformed

        if (generateButtonControl() == true) {
            if (jTextFieldSoundName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please type in Soundname!", "Alert", 2);
            } else {
                generateSound();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a picture!", "Alert", 2);
        }
        System.out.println(advSettings4);
    }//GEN-LAST:event_jButtonGenerateActionPerformed
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
    private javax.swing.JComboBox<String> AlgoComboBox;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButtonPrevious;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JButton jButtonGenerate;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButtonPrevious;
    private javax.swing.JButton jButtonUpdateSoundListe;
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
    private javax.swing.JLabel jLabel_image;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jPanelGrundeinstellungen;
    private javax.swing.JRadioButton jRadioButtonInvers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSliderFrequency;
    private javax.swing.JSlider jSliderSamples;
    private javax.swing.JSlider jSliderVolume;
    private javax.swing.JTable jTable_images;
    private javax.swing.JTable jTable_sound;
    private javax.swing.JTextField jTextFieldPixelInvers;
    private javax.swing.JTextField jTextFieldSoundName;
    // End of variables declaration//GEN-END:variables
}
