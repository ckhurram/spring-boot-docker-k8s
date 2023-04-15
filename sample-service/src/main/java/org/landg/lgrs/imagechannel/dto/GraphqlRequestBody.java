package org.landg.lgrs.imagechannel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraphqlRequestBody {

  private String query;
  private Object variables;
}
