package br.com.nozella.musicxml.service;

import br.com.nozella.musicxml.exception.SystemException;

public interface FileProcessorService {
	
	/**
	 * Esse metodo ira fazer a leitura do diretorio e subdiretorios informado no parametro
	 * <code>directoryPath</code> e gravara o arquivo <b>musicXml.xml</b> no diretorio informado 
	 * pelo parametro <code>path</code> 
	 * 
	 * @param directoryPath local onde estara os arquivos mp3
	 * @param path local onde sera salvo o arquivo musicXml.xml
	 * 
	 * @throws SystemException se ocorrer algum erro na geracao do arquivo XML 
	 */
	void processFile(String directoryPath, String path) throws SystemException;
}
