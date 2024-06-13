package com.willian.financial_organizer.integrationsTest.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperReleaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private ReleaseEmbeddedDTO embeddedDTO;

    public WrapperReleaseDTO() {}

    public ReleaseEmbeddedDTO getEmbeddedDTO() {
        return embeddedDTO;
    }

    public void setEmbeddedDTO(ReleaseEmbeddedDTO embeddedDTO) {
        this.embeddedDTO = embeddedDTO;
    }
}
