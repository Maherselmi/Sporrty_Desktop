package controllers.Authentification;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class sms {
    public static final String ACCOUNT_SID = "ACf151e9085e44178f6e2683f35a20923f";
    public static final String AUTH_TOKEN = "075dd234a602c217c8144271431b63f0";
    public static final String TWILIO_NUMBER = "+13343774121"; // Votre numéro Twilio

    public static void sendSMS(String recipientPhoneNumber, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(recipientPhoneNumber),
                        new com.twilio.type.PhoneNumber(TWILIO_NUMBER),
                        messageBody)
                .create();

        System.out.println("SMS envoyé avec succès : " + message.getSid());
    }
}
