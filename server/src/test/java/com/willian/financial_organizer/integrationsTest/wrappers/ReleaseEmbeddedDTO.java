package com.willian.financial_organizer.integrationsTest.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.willian.financial_organizer.integrationsTest.dtos.ReleasesDTO;

import java.io.Serializable;
import java.util.List;

public class ReleaseEmbeddedDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("releasesDTOList")
    List<ReleasesDTO> releasesDTOList;

    public ReleaseEmbeddedDTO() {
    }

    public List<ReleasesDTO> getReleasesDTOList() {
        return releasesDTOList;
    }

    public void setReleasesDTOList(List<ReleasesDTO> releasesDTOList) {
        this.releasesDTOList = releasesDTOList;
    }
}
