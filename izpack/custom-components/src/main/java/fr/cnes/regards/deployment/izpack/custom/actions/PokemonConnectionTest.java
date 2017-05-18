/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.actions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;

public class PokemonConnectionTest extends ButtonAction {

    private static final String ERROR = "error";

    private static final String DATABASE = "http://www.pokeapi.co/";

    public PokemonConnectionTest(InstallData installData) {
        super(installData);
    }

    @Override
    public boolean execute() {
        boolean reachable = false;
        try {
            URL url = new URL(DATABASE);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if ((responseCode >= 200) && (responseCode < 300)) {
                reachable = true;
            }
        } catch (MalformedURLException e) {
            // Could not generate an URL
        } catch (IOException e) {
            //Could not ping address
        }
        return reachable;
    }

    @Override
    public boolean execute(Console console) {
        if (!execute()) {
            console.println(messages.get(ERROR));
            return false;
        }
        return true;
    }

    @Override
    public boolean execute(Prompt prompt) {
        if (!execute()) {
            prompt.warn(messages.get(ERROR));
            return false;
        }
        return true;
    }
}