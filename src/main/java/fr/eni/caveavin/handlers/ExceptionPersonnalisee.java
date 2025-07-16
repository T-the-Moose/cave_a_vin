package fr.eni.caveavin.handlers;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class ExceptionPersonnalisee {
    // Ici, on peut définir des méthodes pour gérer les exceptions globalement
    // Par exemple, vous pouvez capturer les exceptions spécifiques et renvoyer des réponses personnalisées.

    // Permet de charger les messages du message.properties
    private final MessageSource messageSource;

    public ExceptionPersonnalisee(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // Exemple de méthode pour gérer une exception spécifique
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> capturerException(MethodArgumentNotValidException manve, Locale locale) {
        StringBuilder message = new StringBuilder();

        message.append(messageSource.getMessage("exception.bouteille.titre", null, locale));
        message.append("\n\t");

        // Boucle sur tous les messages d'erreurs
        for (var error: manve.getAllErrors()) {
            message.append(error.getDefaultMessage()).append("\n\t");
        }

        return ResponseEntity
                .badRequest()
                .body(message.toString());
    }
}
