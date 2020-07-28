package utils;

import DTO.ConferenceDetailDTO;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javafx.scene.control.Label;
import org.mindrot.jbcrypt.BCrypt;

public class Utils {
    public static String convertUTF8IntoString(String rawString) {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(rawString);
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }

    public static String TimeFormat(Timestamp timestamp) {
        String hour = new SimpleDateFormat("HH").format(timestamp);
        String minute = new SimpleDateFormat("mm").format(timestamp);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(timestamp);
        return hour + Utils.convertUTF8IntoString("h") + minute
                + Utils.convertUTF8IntoString(" - ") + date;
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password,hash);
    }


    public static BufferedImage createResizedCopy(BufferedImage originalImage, int framesWidth, int framesHeight,
                                                  boolean preserveAlpha) {
        Dimension imageOld = new Dimension(originalImage.getWidth(), originalImage.getHeight());
        Dimension frames = new Dimension(framesWidth, framesHeight);
        Dimension imageNew = getScaledDimension(imageOld, frames);
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        int width = (int) Math.round(imageNew.getWidth());
        int height = (int) Math.round(imageNew.getHeight());
        BufferedImage scaledBI = new BufferedImage(width, height, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return scaledBI;
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    public static TableCell<ConferenceDetailDTO, String> getTableCellCustom() {
        return new TableCell<ConferenceDetailDTO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    super.setText(null);
                    super.setGraphic(null);
                } else {
                    super.setText(null);
                    Label l = new Label(item);
                    l.setWrapText(true);
                    VBox box = new VBox(l);
                    box.setPrefHeight(80);
                    box.setAlignment(Pos.CENTER_LEFT);
                    super.setGraphic(box);
                }
            }
        };
    }

    public static Button getButtonById(String id, List<Button> list){
        for (Button button : list) {
            if (button.getId().equals(id)){
                return button;
            }
        }
        return null;
    }

    //logged menu admin
    public static void loggedMenuAdmin( List<Button> list) {

        getButtonById("btnProfile",list).setVisible(true);
        getButtonById("btnProfile",list).setManaged(true);

        getButtonById("btnAddCfr",list).setVisible(true);
        getButtonById("btnAddCfr",list).setManaged(true);

        getButtonById("btnUserManager",list).setVisible(true);
        getButtonById("btnUserManager",list).setManaged(true);

        getButtonById("btnSignOut",list).setVisible(true);
        getButtonById("btnSignOut",list).setManaged(true);

        getButtonById("btnAppvoral",list).setVisible(true);
        getButtonById("btnAppvoral",list).setManaged(true);

        getButtonById("btnSignIn",list).setVisible(false);
        getButtonById("btnSignIn",list).setManaged(false);
    }

    //logged menu view
    public static void loggedMenuUser(List<Button> list) {

        getButtonById("btnProfile",list).setVisible(true);
        getButtonById("btnProfile",list).setManaged(true);

        getButtonById("btnMyCfr",list).setVisible(true);
        getButtonById("btnMyCfr",list).setManaged(true);

        getButtonById("btnSignOut",list).setVisible(true);
        getButtonById("btnSignOut",list).setManaged(true);

        getButtonById("btnSignIn",list).setVisible(false);
        getButtonById("btnSignIn",list).setManaged(false);
    }

}
