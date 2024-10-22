package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class MioThread extends Thread {
    Socket s = new Socket();

    public MioThread(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            Random random = new Random();
            int numeroDaIndovinare = random.nextInt(100);
            String stringaRicevuta = "";
            boolean nuovaPartita = true;
            boolean numeroValido = true;
            int tentativi = 1;
            while (nuovaPartita == true) {
                do {
                    stringaRicevuta = in.readLine();
                    System.out.println("La stringa ricevuta: " + stringaRicevuta);
                    numeroValido = true;
                    if (stringaRicevuta.compareTo("0") == -1 || stringaRicevuta.compareTo("100") == 1) {
                        numeroValido = false;
                        out.writeBytes("!\n");
                    }
                } while (numeroValido = true);

                if (Integer.parseInt(stringaRicevuta) < numeroDaIndovinare) {
                    out.writeBytes("<\n");
                    tentativi++;
                } else if (Integer.parseInt(stringaRicevuta) > numeroDaIndovinare) {
                    out.writeBytes(">\n");
                    tentativi++;
                } else {
                    out.writeBytes("=\n");
                    out.writeBytes(tentativi + "\n");
                    String contr = in.readLine();
                    if (contr == "s") {
                        tentativi = 1;
                        numeroDaIndovinare = random.nextInt(100);
                    } else
                        nuovaPartita = false;
                }
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}