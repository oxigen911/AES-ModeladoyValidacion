package edu.javeriana.intermediaterouting.service;

import edu.javeriana.intermediaterouting.model.Pago;

public abstract class IntermediateRoutingService
{
   public abstract Pago getInfoFactura( String id );

   public abstract String pagarFactura(String idFactura, Double valueOf);
   
}
