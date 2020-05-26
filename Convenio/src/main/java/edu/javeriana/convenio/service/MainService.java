package edu.javeriana.convenio.service;

public interface MainService<T>
{

	T add( T o );

	T update( T o, int id );

	T getById( int id );

	T deleteById( int id );
}

