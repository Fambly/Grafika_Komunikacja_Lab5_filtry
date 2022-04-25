package company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Main {
    public static JFrame frame;
    public static sztaluga sztlg = new sztaluga();

    public static BufferedImage img1;
    public static BufferedImage img2;
    public static BufferedImage img_prod;

    public static Color ninja = new Color(238, 238, 238);
    public static final String mix = "[Mix] ";
    public static final String lin = "[Lin] ";
    public static String slctd = "";
    public static String[] opcje = {"Wybierz", lin+"Rozjaśnienie/Przyciemnienie", lin+"Negatyw", "Transformacja Potęgowa", mix+"Suma",
            mix+"Odejmowanie", mix+"Różnica", mix+"Mnożenie", mix+"Mnożenie Odwrotności",
            mix+"Negacja", mix+"Ciemniejsze", mix+"Jaśniejsze", mix+"Wyłączanie",
            mix+"Nakładka", mix+"Ostre Światło", mix+"Łagodne Światło", mix+"Rozcieńczenie",
            mix+"Wypalanie", mix+"Reflect Mode", mix+"Przezroczystość"};

    public static void main(String[] args) {
        //wczytuję obraz
        try {
            img1 = ImageIO.read(new File("carcrash.jpg"));
        } catch (IOException e) {
            System.out.println("ERROR "+e+", najprawdopodobniej nie wczytano obrazka!");
        }

        //tworzę okno i elementy
        frame = new JFrame("Niezoptymalizowany photoshop BIEDA edyszyn");
        frame.setSize(630, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JComboBox<String> JCB = new JComboBox<>(opcje);
        JCB.setBounds(15, 60, 220, 20);

        JLabel label1 = new JLabel();
        label1.setBounds(15,100,250,20);
        frame.add(label1);

        JLabel label2 = new JLabel();
        label2.setBounds(15,140,250,20);
        frame.add(label2);

        JTextArea TA1 = new JTextArea();
        TA1.setBounds(15, 120, 220, 20);
        TA1.setBackground(ninja);
        frame.add(TA1);

        JTextArea TA2 = new JTextArea();
        TA2.setBounds(15, 160, 220, 20);
        TA2.setBackground(ninja);
        frame.add(TA2);

        //zmieniam etykiety oraz pola tekstowe w zależności od wyboru użytkownika
        JCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                slctd = (String) JCB.getSelectedItem();

                if (slctd == null) return;
                switch (slctd) {
                    case lin + "Rozjaśnienie/Przyciemnienie" -> {
                        label1.setText("Jasność:");
                        TA1.setBackground(Color.white);
                        TA1.setText("69");
                        label2.setText("Kąt nachylenia:");
                        TA2.setBackground(Color.white);
                        TA2.setText("1.0");
                    }
                    case "Transformacja Potęgowa" -> {
                        label1.setText("C:");
                        TA1.setBackground(Color.white);
                        TA1.setText("0.1");
                        label2.setText("N:");
                        TA2.setBackground(Color.white);
                        TA2.setText("1.8");
                    }
                    case lin + "Negatyw" -> {
                        label1.setText("Brak wartości do manipulowania");
                        TA1.setBackground(ninja);
                        TA1.setText("");
                        label2.setText("");
                        TA2.setBackground(ninja);
                        TA2.setText("");
                    }
                    case mix + "Przezroczystość" -> {
                        label1.setText("Obrazek do zmieszania:");
                        TA1.setBackground(Color.white);
                        TA1.setText("catelk.jpg");
                        label2.setText("Poziom:");
                        TA2.setBackground(Color.white);
                        TA2.setText("6.9");
                    }
                    case "Wybierz" -> {
                        label1.setText("");
                        TA1.setBackground(ninja);
                        TA1.setText("");
                        label2.setText("");
                        TA2.setBackground(ninja);
                        TA2.setText("");
                    }
                    default -> {
                        label1.setText("Obrazek do mieszania:");
                        TA1.setBackground(Color.white);
                        TA1.setText("catelk.jpg");
                        label2.setText("");
                        TA2.setBackground(ninja);
                        TA2.setText("");
                    }
                }
            }
        });
        frame.add(JCB);

        //Tworzę guzik który po naciśnięciu wykona określoną operację
        JButton btn = new JButton();
        btn.setText("Podgląd");
        btn.setBounds(65, 15, 100, 30);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (slctd) {
                    case "Wybierz":
                        label1.setText("");
                        TA1.setText("");
                        TA1.setBackground(ninja);
                        label2.setText("");
                        TA2.setText("");
                        TA2.setBackground(ninja);
                        break;
                    case lin + "Rozjaśnienie/Przyciemnienie":
                        double a = 1.0;
                        int b = 0;

                        try {
                            b = Integer.parseInt(TA1.getText());
                            label1.setText("Jasność:");
                        } catch (Exception expt) {
                            label1.setText("Jasność:                ZŁA WARTOŚĆ!");
                        }
                        try {
                            a = Double.parseDouble(TA2.getText());
                            label2.setText("Kąt nachylenia:");
                        } catch (Exception expt) {
                            label2.setText("Kąt nachylenia:     ZŁA WARTOŚĆ!");
                        }
                        jasno_ciemno(a, b);
                        break;

                    case lin + "Negatyw":
                        negatyw();
                        break;

                    case "Transformacja Potęgowa":
                        double c = 1.0;
                        double n = 0.0;

                        try {
                            c = Double.parseDouble(TA1.getText());
                            label1.setText("C:");
                        } catch (Exception expt) {
                            label1.setText("C:                ZŁA WARTOŚĆ!");
                        }
                        try {
                            n = Double.parseDouble(TA2.getText());
                            label2.setText("N:");
                        } catch (Exception expt) {
                            label2.setText("N:                ZŁA WARTOŚĆ!");
                        }
                        transform_poteg(c, n);
                        break;

                    case mix + "Przezroczystość":
                        double alpha = 1.0;

                        try {
                            alpha = Double.parseDouble(TA2.getText());
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label2.setText("Poziom:");
                        } catch (Exception expt) {
                            label2.setText("Poziom:        ZŁA WARTOŚĆ!");
                        }
                        przezroczystosc(img2, alpha);
                        break;

                    case mix + "Suma":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        suma(img2);
                        break;

                    case mix + "Odejmowanie":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        odejmowanie(img2);
                        break;

                    case mix + "Różnica":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        roznica(img2);
                        break;

                    case mix + "Mnożenie":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        mnozenie(img2);
                        break;

                    case mix + "Mnożenie Odwrotności":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        revMnoz(img2);
                        break;

                    case mix + "Negacja":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        negacja(img2);
                        break;

                    case mix + "Ciemniejsze":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        ciemniejsze(img2);
                        break;

                    case mix + "Jaśniejsze":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        jasniejsze(img2);
                        break;

                    case mix + "Wyłączanie":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        wylaczanie(img2);
                        break;

                    case mix + "Nakładka":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        nakladka(img2);
                        break;

                    case mix + "Ostre Światło":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        ostre_swiatlo(img2);
                        break;

                    case mix + "Łagodne Światło":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        easy_light(img2);
                        break;

                    case mix + "Rozcieńczenie":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        rozcienczenie(img2);
                        break;

                    case mix + "Wypalanie":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        wypalenie(img2);
                        break;

                    case mix + "Reflect Mode":
                        try {
                            img2 = ImageIO.read(new File(TA1.getText()));
                            label1.setText("Obrazek do mieszania:");
                        } catch (Exception expt) {
                            label1.setText("Obrazek do mieszania:  ZŁA WARTOŚĆ!");
                        }
                        reflect_mode(img2);
                        break;
                }
            }
        });
        frame.add(btn);

        frame.add(sztlg);
        frame.setVisible(true);
    }

    //metoda służąca do kopiowania obrazu i modyfikacji go bez naruszania wartości orginału
    public static BufferedImage cache_img(BufferedImage buff_img) {
        ColorModel cm = buff_img.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = buff_img.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    //Kolejno stosowanie wzorów z lab5.pdf

    public static void jasno_ciemno(double a, int b) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));

                int red = (int) (color.getRed() * a) + b;
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = (int) (color.getGreen() * a) + b;
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = (int) (color.getBlue() * a) + b;
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void negatyw() {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));

                int red = 255 - color.getRed();
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = 255 - color.getGreen();
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = 255 - color.getBlue();
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void transform_poteg(double c, double n) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));

                int red = (int) Math.pow((color.getRed() * c), n);
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = (int) Math.pow((color.getGreen() * c), n);
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = (int) Math.pow((color.getBlue() * c), n);
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void suma(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = color.getRed() + color2.getRed();
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = color.getGreen() + color2.getGreen();
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = color.getBlue() + color2.getBlue();
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void odejmowanie(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = color.getRed() + color2.getRed() - 1;
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = color.getGreen() + color2.getGreen() - 1;
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = color.getBlue() + color2.getBlue() - 1;
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void roznica(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = Math.abs(color.getRed() - color2.getRed());
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = Math.abs(color.getGreen() - color2.getGreen());
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = Math.abs(color.getBlue() - color2.getBlue());
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void mnozenie(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = color.getRed() * color2.getRed();
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = color.getGreen() * color2.getGreen();
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = color.getBlue() * color2.getBlue();
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void revMnoz(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = (1 - (1 - color.getRed()) * (1 - color2.getRed()));
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = (1 - (1 - color.getGreen()) * (1 - color2.getGreen()));
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = (1 - (1 - color.getBlue()) * (1 - color2.getBlue()));
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void negacja(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = 1 - Math.abs(1 - color.getRed() - color2.getRed());
                if (red < 0) red = 0;
                int green = 1 - Math.abs(1 - color.getGreen() - color2.getGreen());
                if (green < 0) green = 0;
                int blue = 1 - Math.abs(1 - color.getBlue() - color2.getBlue());
                if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void ciemniejsze(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = color2.getRed();
                if (color.getRed() < color2.getRed()) {
                    red = color.getRed();
                }
                int green = color2.getGreen();
                if (color.getGreen() < color2.getGreen()) {
                    green = color.getGreen();
                }
                int blue = color2.getBlue();
                if (color.getBlue() < color2.getBlue()) {
                    blue = color.getBlue();
                }

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void jasniejsze(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = color2.getRed();
                if (color.getRed() > color2.getRed()) {
                    red = color.getRed();
                }
                int green = color2.getGreen();
                if (color.getGreen() > color2.getGreen()) {
                    green = color.getGreen();
                }
                int blue = color2.getBlue();
                if (color.getBlue() > color2.getBlue()) {
                    blue = color.getBlue();
                }

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void wylaczanie(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = color.getRed() + color2.getRed() - (2 * color.getRed() * color2.getRed());
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = color.getGreen() + color2.getGreen() - (2 * color.getGreen() * color2.getGreen());
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = color.getBlue() + color2.getBlue() - (2 * color.getBlue() * color2.getBlue());
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void nakladka(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = 1 - (2 * (1 - color.getRed()) * (1 - color2.getRed()));
                if (color.getRed() < 255 * 0.5) {
                    red = 2 * color.getRed() * color2.getRed();
                }
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = 1 - (2 * (1 - color.getGreen()) * (1 - color2.getGreen()));
                if (color.getGreen() < 255 * 0.5) {
                    green = 2 * color.getGreen() * color2.getGreen();
                }
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = 1 - (2 * (1 - color.getBlue()) * (1 - color2.getBlue()));
                if (color.getBlue() < 255 * 0.5) {
                    blue = 2 * color.getBlue() * color2.getBlue();
                }
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void ostre_swiatlo(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = 1 - (2 * (1 - color.getRed()) * (1 - color2.getRed()));
                if (color2.getRed() < 255 * 0.5) {
                    red = 2 * color.getRed() * color2.getRed();
                }
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = 1 - (2 * (1 - color.getGreen()) * (1 - color2.getGreen()));
                if (color2.getGreen() < 255 * 0.5) {
                    green = 2 * color.getGreen() * color2.getGreen();
                }
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = 1 - (2 * (1 - color.getBlue()) * (1 - color2.getBlue()));
                if (color2.getBlue() < 255 * 0.5) {
                    blue = 2 * color.getBlue() * color2.getBlue();
                }
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void easy_light(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = (int) (Math.sqrt(color.getRed()) * (2 * color2.getRed() - 1) + (2 * color.getRed()) * (1 - color2.getRed()));
                if (color2.getRed() < 255 * 0.5) {
                    red = (int) (2 * color.getRed() * color2.getRed() + Math.pow(color.getRed(), 2) * (1 - 2 * color2.getRed()));
                }
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = (int) (Math.sqrt(color.getGreen()) * (2 * color2.getGreen() - 1) + (2 * color.getGreen()) * (1 - color2.getGreen()));
                if (color2.getGreen() < 255 * 0.5) {
                    green = (int) (2 * color.getGreen() * color2.getGreen() + Math.pow(color.getGreen(), 2) * (1 - 2 * color2.getGreen()));
                }
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = (int) (Math.sqrt(color.getBlue()) * (2 * color2.getBlue() - 1) + (2 * color.getBlue()) * (1 - color2.getBlue()));
                if (color2.getBlue() < 255 * 0.5) {
                    blue = (int) (2 * color.getBlue() * color2.getBlue() + Math.pow(color.getBlue(), 2) * (1 - 2 * color2.getBlue()));
                }
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void rozcienczenie(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int temp = 1 - color2.getRed();
                if (temp == 0) {
                    temp = 1;
                }
                int red = color.getRed() / (temp);
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                temp = 1 - color2.getGreen();
                if (temp == 0) {
                    temp = 1;
                }
                int green = color.getGreen() / (temp);
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                temp = 1 - color2.getBlue();
                if (temp == 0) {
                    temp = 1;
                }
                int blue = color.getBlue() / (temp);
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void wypalenie(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int temp = color2.getRed();
                if (temp == 0) {
                    temp = 1;
                }
                int red = 1 - (1 - color.getRed()) / temp;
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                temp = color2.getGreen();
                if (temp == 0) {
                    temp = 1;
                }
                int green = 1 - (1 - color.getGreen()) / temp;
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                temp = color2.getBlue();
                if (temp == 0) {
                    temp = 1;
                }
                int blue = 1 - (1 - color.getBlue()) / temp;
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void reflect_mode(BufferedImage img2) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int temp = color2.getRed();
                if (temp==1) {
                    temp = 0;
                }
                int red = (int) (Math.pow(color.getRed(), 2) / (1 - temp));
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                temp = color2.getGreen();
                if (temp==1) {
                    temp = 0;
                }
                int green = (int) (Math.pow(color.getGreen(), 2) / (1 - temp));
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                temp = color2.getBlue();
                if (temp==1) {
                    temp = 0;
                }
                int blue = (int) (Math.pow(color.getBlue(), 2) / (1 - temp));
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }

    public static void przezroczystosc(BufferedImage img2, double alpha) {
        img_prod = cache_img(img1);

        for (int i = 0; i < img_prod.getWidth(); i++) {
            for (int j = 0; j < img_prod.getHeight(); j++) {
                Color color = new Color(img_prod.getRGB(i, j));
                Color color2 = new Color(img2.getRGB(i, j));

                int red = (int) ((1 - alpha) * color2.getRed() + alpha * color.getRed());
                if (red > 255) red = 255;
                else if (red < 0) red = 0;
                int green = (int) ((1 - alpha) * color2.getGreen() + alpha * color.getGreen());
                if (green > 255) green = 255;
                else if (green < 0) green = 0;
                int blue = (int) ((1 - alpha) * color2.getBlue() + alpha * color.getBlue());
                if (blue > 255) blue = 255;
                else if (blue < 0) blue = 0;

                Color color_final = new Color(red, green, blue);
                img_prod.setRGB(i, j, color_final.getRGB());
            }
        }
        sztlg.repaint();
    }
}

class sztaluga extends Canvas {
    @Override
    public void paint(Graphics g) {
        Graphics2D G2D = (Graphics2D) g;
        G2D.drawImage(Main.img1, 300, 15, null);
        G2D.drawImage(Main.img_prod, 300, 330, null);
    }
}