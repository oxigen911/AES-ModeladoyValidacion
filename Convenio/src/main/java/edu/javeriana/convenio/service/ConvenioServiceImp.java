package edu.javeriana.convenio.service;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.javeriana.convenio.exception.ResourceNotFoundException;
import edu.javeriana.convenio.model.Convenio;
import edu.javeriana.convenio.repository.ConvenioRepository;

import java.util.List;

@SuppressWarnings("unchecked")
@Service
@NoArgsConstructor
public class ConvenioServiceImp extends ConvenioService {

   @Autowired
   private ConvenioRepository convenioRepository;

   @Override
   public Convenio add(Convenio o) {
      return convenioRepository.save(o);
   }

   @Override
   public Convenio update(Convenio o, int id) {
      Convenio convenio = checkIfIdIsPresentandReturnConvenio(id);
      convenio.setNombre(o.getNombre());
      convenio.setHost(o.getHost());
      convenio.setPuerto(o.getPuerto());
      convenio.setUrlServicio(o.getUrlServicio());
      convenio.setTipo(o.getTipo());
      return convenioRepository.save(convenio);
   }

   @Override
   public Convenio getById(int id) {
      return checkIfIdIsPresentandReturnConvenio(id);
   }

   @Override
   public Convenio deleteById(int id) {
      Convenio convenio = checkIfIdIsPresentandReturnConvenio(id);
      convenioRepository.deleteById(id);
      return convenio;
   }

   private Convenio checkIfIdIsPresentandReturnConvenio(int id) {
      if (!convenioRepository.findById(id).isPresent())
         throw new ResourceNotFoundException(" Convenio id = " + id + " not found");
      else
         return convenioRepository.findById(id).get();
   }
}