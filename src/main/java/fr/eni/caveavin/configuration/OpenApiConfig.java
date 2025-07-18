package fr.eni.caveavin.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Documentation pour le projet Cave à Vin API",
                version = "1.0",
                description = "Exemple de documentation pour l'API pour la gestion d'une cave à vin"
        )
)
public class OpenApiConfig {
    // Cette classe est utilisée pour configurer OpenAPI pour l'application.
    // Elle permet de définir les métadonnées de l'API, telles que le titre, la version et la description.
    // Aucune méthode n'est nécessaire ici car les annotations gèrent la configuration.
}
