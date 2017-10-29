package com.inpowered.model.core.buiders;

import com.inpowered.model.core.AylienInfo;

public class AylienInfoBuilder {

	public AylienInfo build(Double polarityConfidence, Double subjectivityConfidence, String subjectivity)
	{
		return new AylienInfo(polarityConfidence, subjectivityConfidence, subjectivity);
	}
}
