/*
 *  Copyright 2009,2010 Martin Roth (mhroth@gmail.com)
 * 
 *  This file is part of JAsioHost.
 *
 *  JAsioHost is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JAsioHost is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with JAsioHost.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.synthbot.jasiohost;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

/**
 * The <code>ExampleHost</code> demonstrates how to use an <code>AsioDriver</code> in order to read 
 * and write audio from a loaded ASIO driver. A small GUI is presented, allowing the user to select
 * any of the available ASIO drivers on the system. The <i>Start</i> button loads the driver and 
 * plays a 440Hz tone. The <i>Stop</i> button stops this process and unloads the driver. The
 * <i>Control Panel</i> button opens the driver's control panel for any additional configuration.
 */
public class ExampleHost extends JFrame implements AsioDriverListener {
  
    private static final long serialVersionUID = 1L;

    private AsioDriver asioDriver;
    private Set<AsioChannel> activeChannels;
    private int sampleIndex;
    private int bufferSize;
    private double sampleRate;
    private float[] output;

    // Variables declaration
    private javax.swing.JButton btnSpeaker1;
    private javax.swing.JButton btnSpeaker2;
    private javax.swing.JButton btnSpeaker3;
    private javax.swing.JButton btnSpeaker4;
    private javax.swing.JButton btnSpeaker5;
    private javax.swing.JButton btnSpeaker6;
    private javax.swing.JButton btnSpeaker7;
    private javax.swing.JButton btnSpeaker8;
    private javax.swing.JButton controlPanelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField freqSpeaker1;
    private javax.swing.JTextField freqSpeaker2;
    private javax.swing.JTextField freqSpeaker3;
    private javax.swing.JTextField freqSpeaker4;
    private javax.swing.JTextField freqSpeaker5;
    private javax.swing.JTextField freqSpeaker6;
    private javax.swing.JTextField freqSpeaker7;
    private javax.swing.JTextField freqSpeaker8;
    // End of variables declaration

    public ExampleHost() {
        initComponents();

        activeChannels = new HashSet<AsioChannel>();

        final JComboBox comboBox = new JComboBox(AsioDriver.getDriverNames().toArray());
        final AsioDriverListener host = this;
        asioDriver = AsioDriver.getDriver(comboBox.getSelectedItem().toString());
        asioDriver.addAsioDriverListener(host);
        bufferSize = asioDriver.getBufferPreferredSize();
        sampleRate = asioDriver.getSampleRate();
        /*output = new float[bufferSize];
        asioDriver.createBuffers(activeChannels);
        asioDriver.start();*/

        comboBox.setSelectedIndex(3);

        activeChannels.add(asioDriver.getChannelOutput(0));
        activeChannels.add(asioDriver.getChannelOutput(1));
        activeChannels.add(asioDriver.getChannelOutput(2));
        activeChannels.add(asioDriver.getChannelOutput(3));
        activeChannels.add(asioDriver.getChannelOutput(4));
        activeChannels.add(asioDriver.getChannelOutput(5));
        activeChannels.add(asioDriver.getChannelOutput(6));
        activeChannels.add(asioDriver.getChannelOutput(7));

        System.out.println ( comboBox.getSelectedItem().toString() );
        asioDriver = AsioDriver.getDriver(comboBox.getSelectedItem().toString());
    }

    private void initComponents() {

        jLabel2 = new JLabel();
        jSeparator1 = new JSeparator();
        jLabel1 = new JLabel();
        freqSpeaker1 = new JTextField();
        btnSpeaker1 = new JButton();
        jSeparator2 = new JSeparator();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        freqSpeaker2 = new JTextField();
        btnSpeaker2 = new JButton();
        jLabel5 = new JLabel();
        freqSpeaker3 = new JTextField();
        jLabel6 = new JLabel();
        jSeparator3 = new JSeparator();
        btnSpeaker3 = new JButton();
        jLabel7 = new JLabel();
        freqSpeaker4 = new JTextField();
        jLabel8 = new JLabel();
        jSeparator4 = new JSeparator();
        btnSpeaker4 = new JButton();
        jLabel9 = new JLabel();
        freqSpeaker5 = new JTextField();
        jLabel10 = new JLabel();
        jSeparator5 = new JSeparator();
        btnSpeaker5 = new JButton();
        jLabel11 = new JLabel();
        freqSpeaker6 = new JTextField();
        jLabel12 = new JLabel();
        jSeparator6 = new JSeparator();
        btnSpeaker6 = new JButton();
        jLabel13 = new JLabel();
        freqSpeaker7 = new JTextField();
        jLabel14 = new JLabel();
        jSeparator7 = new JSeparator();
        btnSpeaker7 = new JButton();
        jLabel15 = new JLabel();
        freqSpeaker8 = new JTextField();
        jLabel16 = new JLabel();
        jSeparator8 = new JSeparator();
        btnSpeaker8 = new JButton();
        controlPanelButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Speakers 1");

        jLabel1.setText("Freq:");

        freqSpeaker1.setText("freqSpeaker1");

        btnSpeaker1.setText("Start/Stop");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Speakers 1");

        jLabel4.setText("Freq:");

        freqSpeaker2.setText("freqSpeaker1");

        btnSpeaker2.setText("Start/Stop");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Speakers 1");

        freqSpeaker3.setText("freqSpeaker1");

        jLabel6.setText("Freq:");

        btnSpeaker3.setText("Start/Stop");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Speakers 1");

        freqSpeaker4.setText("freqSpeaker1");

        jLabel8.setText("Freq:");

        btnSpeaker4.setText("Start/Stop");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Speakers 1");

        freqSpeaker5.setText("freqSpeaker1");

        jLabel10.setText("Freq:");

        btnSpeaker5.setText("Start/Stop");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Speakers 1");

        freqSpeaker6.setText("freqSpeaker1");

        jLabel12.setText("Freq:");

        btnSpeaker6.setText("Start/Stop");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Speakers 1");

        freqSpeaker7.setText("freqSpeaker1");

        jLabel14.setText("Freq:");

        btnSpeaker7.setText("Start/Stop");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Speakers 1");

        freqSpeaker8.setText("freqSpeaker1");

        jLabel16.setText("Freq:");

        btnSpeaker8.setText("Start/Stop");

        controlPanelButton.setText("Control Panel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jSeparator1)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel2)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel1)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(freqSpeaker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnSpeaker1)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jSeparator2)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel4)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(freqSpeaker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnSpeaker2)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jSeparator3)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel6)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(freqSpeaker3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnSpeaker3)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jSeparator4)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel7)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel8)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(freqSpeaker4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnSpeaker4)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jSeparator5)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel9)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel10)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(freqSpeaker5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnSpeaker5)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jSeparator6)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel11)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel12)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(freqSpeaker6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnSpeaker6)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jSeparator7)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel13)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel14)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(freqSpeaker7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnSpeaker7)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jSeparator8)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel15)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel16)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(freqSpeaker8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(63, 63, 63)
                                                        .addComponent(btnSpeaker8))))
                                .addContainerGap(324, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(controlPanelButton)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(controlPanelButton)
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(freqSpeaker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSpeaker1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(freqSpeaker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSpeaker2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(freqSpeaker3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSpeaker3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8)
                                        .addComponent(freqSpeaker4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSpeaker4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10)
                                        .addComponent(freqSpeaker5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSpeaker5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12)
                                        .addComponent(freqSpeaker6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSpeaker6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel14)
                                        .addComponent(freqSpeaker7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSpeaker7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel16)
                                        .addComponent(freqSpeaker8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSpeaker8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    public void bufferSwitch(long systemTime, long samplePosition, Set<AsioChannel> channels) {
        for (int i = 0; i < bufferSize; i++, sampleIndex++) {
            output[i] = (float) Math.sin(2 * Math.PI * sampleIndex * 100.0 / sampleRate);
            System.out.println (output[i]);
        }
        for (AsioChannel channelInfo : channels) {

            System.out.println("Index: " + channelInfo.getChannelIndex() + ", Name: " + channelInfo.getChannelName());

        /*if (channelInfo.getChannelIndex() == 7){
            channelInfo.write(output);
        } else if(channelInfo.getChannelIndex() == 4){
            channelInfo.write(output);
        } else if ( channelInfo.getChannelIndex() == 2){*/
            channelInfo.write(output);
        /*}*/
        }

    }

    public void bufferSizeChanged(int bufferSize) {
        System.out.println("bufferSizeChanged() callback received.");
    }

    public void latenciesChanged(int inputLatency, int outputLatency) {
        System.out.println("latenciesChanged() callback received.");
    }

    public void resetRequest() {
        /*
         * This thread will attempt to shut down the ASIO driver. However, it will
         * block on the AsioDriver object at least until the current method has returned.
         */
        new Thread() {
            @Override
            public void run() {
                System.out.println("resetRequest() callback received. Returning driver to INITIALIZED state.");
                asioDriver.returnToState(AsioDriverState.INITIALIZED);
            }
        }.start();
    }

    public void resyncRequest() {
        System.out.println("resyncRequest() callback received.");
    }

    public void sampleRateDidChange(double sampleRate) {
        System.out.println("sampleRateDidChange() callback received.");
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        ExampleHost host = new ExampleHost();
        host.setVisible(true);
    }

}
