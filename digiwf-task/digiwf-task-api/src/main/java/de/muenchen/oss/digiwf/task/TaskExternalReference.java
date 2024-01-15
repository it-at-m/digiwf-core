package de.muenchen.oss.digiwf.task;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents an external referencable identity.
 * <code>
 *   [{
 *     "type": "dms"
 *     "identity": "981726313829"
 *   },
 *   {
 *     "type": "zammat.was.auch.immer"
 *     "identity": "123-1234-3452/task/123092813/user/12312"
 *   },
 *   {
 *     "type": "url"
 *     "identity": "https://www.google.de/?search=foo"
 *   }]
 * </code>
 */
@RequiredArgsConstructor
@Builder
@Jacksonized
@Data
public class TaskExternalReference {
  /**
   * Reference type.
   */
  private final String type;
  /**
   * Referenced identity.
   */
  private final String identity;
}
