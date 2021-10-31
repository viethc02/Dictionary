import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class SoundSystem {
    public static void TurnOnSpeech(String s) {
        try
        {
            //setting properties as Kevin Dictionary
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");

            //registering speech engine
            Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");

            //create a Synthesizer that generates voice
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

            //allocates a synthesizer
            synthesizer.allocate();

            //speak the specified text until the QUEUE become empty
            synthesizer.speakPlainText(s, null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

//references
//https://www.javatpoint.com/convert-text-to-speech-in-java?fbclid=IwAR051qZzv7W-JnHivA71_E5nIvOnpr-DxT0xtzJjutAb1IlJp5ayPAAxrGg
