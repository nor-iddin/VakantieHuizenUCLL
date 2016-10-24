package versie1;

public class DatumKonNietOpgehaaldWordenException extends Exception{
	public DatumKonNietOpgehaaldWordenException(String errormessage){
		super(errormessage);
	}
}

public class LeegDatumObjectException extends Exception{
	public LeegDatumObjectException(String errormessage){
		super(errormessage);
	}
}

public class OngeldigJaarDatumObjectException extends Exception{
	public OngeldigJaarDatumObjectException(String errormessage){
		super(errormessage);
	}
}

public class OngeldigeMaandDatumException extends Exception{
	public OngeldigeMaandDatumException(String errormessage){
		super(errormessage);
	}
}

public class OngeldigeDagDatumException extends Exception{
	public OngeldigeDagDatumException(String errormessage){
		super(errormessage);
	}
}

public class OngeldigeParameterDatumException extends Exception{
	public OngeldigeParameterDatumException(String errormessage){
		super(errormessage);
	}
}



