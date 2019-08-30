package keylistenernative;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * @author David Salazar Mejia
 * @correo davidsm54@gmail.com
 * @fecha 30/08/2019
 * @hora 10:59:12 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 */
public class KeyListenerNative implements NativeKeyListener {

    private static String typed = "";
    private static String pressed = "";

    private static String rutaArchivo = "C:\\Users\\SOMA-DAVID\\Desktop\\tsjs.txt";

    private static FileWriter archivotxt = null;

    private static PrintWriter escritor = null;

    private static Integer contador = 0;

    private static Integer indice = 0;

    private static final List<Integer> lCode = Arrays.asList(new Integer[]{15, 29, 40});

    private static void cierraArchivoAutomatico() {
        Thread hilo = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(KeyListenerNative.class.getName()).log(Level.SEVERE, null, ex);
                }
                contador++;
                System.err.println("\n\n\nCOntador: " + contador + "\n\n\n");

                if (contador >= 5 && archivotxt != null) {
                    try {
                        archivotxt.close();
                        System.exit(0);
                    } catch (IOException ex) {
                        Logger.getLogger(KeyListenerNative.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        hilo.start();
    }

    private static void escribeArchivo() {
        if (archivotxt == null && escritor == null) {
            File archivo = new File(rutaArchivo);

            if (archivo.exists()) {
                System.err.println("Ya existe un archivo con ese nombre");
                System.exit(0);
                return;
            }

            try {
                archivotxt = new FileWriter("C:\\Users\\SOMA-DAVID\\Desktop\\log.txt");

                escritor = new PrintWriter(archivotxt);
                cierraArchivoAutomatico();
            } catch (IOException ex) {
                Logger.getLogger(KeyListenerNative.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        escritor.println("TYPED: " + typed + "\n"
                + "PRESSED: " + pressed + "\n\n");

        contador = 0;
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
//            Logger.getLogger(KeyListenerNative.class.getName()).log(Level.SEVERE, null, ex);
        }

        GlobalScreen.addNativeKeyListener(new KeyListenerNative());
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        Integer keyCode = e.getKeyCode();

        if (lCode.contains(keyCode)) {
            return;
        }

        switch (e.getKeyCode()) {
            case 58:
                KeyListenerNative.typed += "||";
                break;
            case 57:
                indice++;
                KeyListenerNative.typed += " ";
                break;
            case 46:
                try {
                    if (indice > 0) {
                        KeyListenerNative.typed = KeyListenerNative.typed.substring(0, typed.length() - 1);
                    }
                } catch (Exception ex) {
                    System.err.println("errorrr:" + ex.getStackTrace());
                }
                break;
            case 14:
                try {
                    if (indice > 0) {
                        KeyListenerNative.typed = KeyListenerNative.typed.substring(0, typed.length() - 1);
                    }

                } catch (Exception ex) {
                    System.err.println("errorrr:" + ex.getStackTrace());
                }
                break;
            case 28:
                indice++;
                KeyListenerNative.typed += "\n";
                break;
            case 42:
                KeyListenerNative.typed += "|";
                break;
            default:
                indice++;
                KeyListenerNative.typed += NativeKeyEvent.getKeyText(e.getKeyCode());
        }

        KeyListenerNative.escribeArchivo();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        Integer keyCode = e.getKeyCode();

        if (lCode.contains(keyCode)) {
            return;
        }

        switch (e.getKeyCode()) {
            case 58:
                KeyListenerNative.pressed += "||";
                break;
            case 57:
                indice++;
                KeyListenerNative.pressed += " ";
                break;
            case 45:
                KeyListenerNative.pressed += "X";
                break;
            case 41:
                KeyListenerNative.pressed += "Ñ";
                break;
            case 3667:
                try {
                    if (indice > 0) {
                        KeyListenerNative.pressed = KeyListenerNative.pressed.substring(0, pressed.length() - 1);
                    }
                } catch (Exception ex) {
                    System.err.println("errorrr:" + ex.getStackTrace());
                }
                break;
            case 14:
                try {
                    if (indice > 0) {
                        KeyListenerNative.pressed = KeyListenerNative.pressed.substring(0, pressed.length() - 1);
                    }

                } catch (Exception ex) {
                    System.err.println("errorrr:" + ex.getStackTrace());
                }
                break;
            case 28:
                indice++;
                KeyListenerNative.pressed += "\n";
                break;
            case 42:
                KeyListenerNative.pressed += "|";
                break;
            default:
                indice++;
                KeyListenerNative.pressed += NativeKeyEvent.getKeyText(e.getKeyCode());
        }

        KeyListenerNative.escribeArchivo();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        return;
    }
}
