package de.muenchen.oss.digiwf.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

/**
 * Represents an external referencable identity.
 * <code>
 *   [{
 *     "type": "dms"
 *     "identity": "981726313829"
 *   },
 *   {
 *     "type": "zammat"
 *     "identity": "123-1234-3452/task/123092813/user/12312"
 *   },
 *   {
 *     "type": "url"
 *     "identity": "[Label](https://www.google.de/?search=foo)"
 *   }]
 * </code>
 */
@AllArgsConstructor
@Builder
@Jacksonized
@Data
public class TaskExternalReference {
  /**
   * Reference type.
   */
  private String type;
  /**
   * Referenced identity.
   */
  private String identity;
}
