package edu.javeriana.intermediaterouting.controller;

import static edu.javeriana.intermediaterouting.constants.ApiConstants.MESSAGE_FOR_REGEX_NUMBER_MISMATCH;
import static edu.javeriana.intermediaterouting.constants.ApiConstants.REGEX_FOR_NUMBERS;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.javeriana.intermediaterouting.model.Pago;
import edu.javeriana.intermediaterouting.results.ResponseWrapper;
import edu.javeriana.intermediaterouting.service.IntermediateRoutingService;

@Validated
@RestController
@RequestMapping("/api/v1/intermediateRouting")
public class IntermediateRoutingController {

   @Autowired
   private IntermediateRoutingService intermediateRoutingService;

   @GetMapping(value = "/obtenerFactura/{id}")
   public ResponseWrapper<Pago> getInfoFactura(
         @Valid @Pattern(regexp = REGEX_FOR_NUMBERS, message = MESSAGE_FOR_REGEX_NUMBER_MISMATCH) @PathVariable(value = "id") String id) {
      return new ResponseWrapper<>(intermediateRoutingService.getInfoFactura(id), HttpStatus.OK);
   }

   @GetMapping(value = "/pagarFactura/{idFactura}/{valorPago}")
   public ResponseWrapper<String> pagarFactura(@Valid @PathVariable(value = "idFactura") String idFactura,
         @PathVariable(value = "valorPago") String valorPago) {
      return new ResponseWrapper<>(intermediateRoutingService.pagarFactura(idFactura,Double.valueOf(valorPago)), HttpStatus.OK);
   }

}
