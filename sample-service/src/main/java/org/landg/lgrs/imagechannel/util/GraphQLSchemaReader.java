package org.landg.lgrs.imagechannel.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public final class GraphQLSchemaReader {

  private GraphQLSchemaReader() {
    throw new IllegalStateException("Utility class");
  }

  public static String getSchemaFromFileName(final String filename)  {
    try {
      return new String(
          GraphQLSchemaReader.class.getClassLoader().getResourceAsStream("graphql/" + filename + ".graphql").readAllBytes());
    } catch (IOException e) {
      throw new IllegalStateException("Unable to read graphql query");
    }

  }

}
