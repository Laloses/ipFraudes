package com.ibm.academia.restapi.ipfraude.modelo.repositorios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.restapi.ipfraude.modelo.entidades.BlackList;

@Repository
public interface BlackListRepository extends CrudRepository<BlackList, Long> {
	
	public Optional<BlackList> findByIp(String ip);
	public Optional<BlackList> findByIpAndBanned(String ip, Boolean banned);

}
