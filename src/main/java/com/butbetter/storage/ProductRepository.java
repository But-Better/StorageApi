package com.butbetter.storage;

import com.butbetter.storage.model.ProductInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<ProductInformation, UUID> {
	
	/*void deleteAllByIdInBatch(Iterable<ID> ids);
	void deleteAllInBatch();
	void deleteAllInBatch(Iterable<ProductInformation> entities);
	List<P> findAll();

	<S extends P>
	List<S> findAll(Example<S> example);
	<S extends P>
	List<S> findAll(Example<S> example, Sort sort);

	List<P> findAll(Sort sort);
	List<P> findAllById(Iterable<UUID> ids);

	void flush();
	P getById(UUID id);



	<S extends P>
	List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends P>
	S saveAndFlush(S entity);*/
}