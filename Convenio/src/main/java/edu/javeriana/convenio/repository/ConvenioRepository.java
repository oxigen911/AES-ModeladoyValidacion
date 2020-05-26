package edu.javeriana.convenio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.javeriana.convenio.model.Convenio;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Integer>
{

}
