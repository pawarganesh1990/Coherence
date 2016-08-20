package com.javahonk.pricecacheserializers;

import java.io.IOException;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofSerializer;
import com.tangosol.io.pof.PofWriter;

public class PricesSerializer implements PofSerializer {

	public Object deserialize(PofReader pofReader) 
			throws IOException {
		
		int count = 0;
		
		String symbol = pofReader.readString(count++);
		String cusip = pofReader.readString(count++);
		String compositeRicCode = pofReader.readString(count++);
		double closingPrice = pofReader.readDouble(count++);
		String closingDate = pofReader.readString(count++);
			
		pofReader.readRemainder();
		
		return new Prices(
				symbol, 
				cusip, 
				compositeRicCode, 
				closingPrice, 
				closingDate
				);
	}

	public void serialize(PofWriter pofWriter, Object object) 
			throws IOException {
		
		Prices prices = (Prices) object;
		
		int count = 0;
		
		pofWriter.writeString(count++, prices.getSymbol());
		pofWriter.writeString(count++, prices.getCusip());
		pofWriter.writeString(count++, prices.getCompositeRicCode());
		pofWriter.writeDouble(count++, prices.getClosingPrice());
		pofWriter.writeString(count++, prices.getClosingDate());
		
		pofWriter.writeRemainder(null);
		
	}
	
	

}
