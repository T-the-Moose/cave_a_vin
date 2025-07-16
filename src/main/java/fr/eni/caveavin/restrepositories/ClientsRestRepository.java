package fr.eni.caveavin.restrepositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "client", collectionResourceRel = "client")
public interface ClientsRestRepository {
}
