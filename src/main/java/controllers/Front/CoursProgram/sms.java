package controllers.Front.CoursProgram;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class sms {
    public static final String ACCOUNT_SID = "AC2e92ecc0b3606c76ce0312f4cd3b3493";
    public static final String AUTH_TOKEN = "e21bd7da071b232df4d37cf2835b8c82";
    public static final String TWILIO_NUMBER = "+18606154476"; // Votre numéro Twilio

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
