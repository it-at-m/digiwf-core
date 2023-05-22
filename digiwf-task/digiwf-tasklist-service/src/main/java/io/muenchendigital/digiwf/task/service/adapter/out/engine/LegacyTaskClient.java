package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.configuration.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @deprecated  legacy adapter to support old schema tasks.
 * Will be removed as soon as all processes have been migrated to schema-based forms.
 */
@Deprecated
@FeignClient(
    name = "${feign.client.config.legacy-task.name:legacy-task}",
    url = "${feign.client.config.legacy-task.url:${feign.client.config.default.url:http://localhost:8080/engine-rest}}",
    configuration = {ClientConfiguration.class}
)
public interface LegacyTaskClient {

  @RequestMapping(value = "/rest/task", method = {RequestMethod.PUT}, produces = {"application/json"})
  void saveTask(@Valid @RequestBody SaveTO save);

  @RequestMapping(value = "/rest/task", method = {RequestMethod.POST}, produces = {"application/json"})
  void completeTask(@Valid @RequestBody final CompleteTO completeTO);

  @RequestMapping("/rest/form/{key}")
  FormTO getForm(@PathVariable("key") String key);

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class SaveTO {

    /**
     * Id of the task.
     */
    @NotBlank
    private String taskId;

    /**
     * Variables that are saved.
     * Only variables that are contained in the associated form are valid.
     */
    private Map<String, Object> variables;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class CompleteTO {

    /**
     * Id of the task the should be completed.
     */
    @NotBlank
    private String taskId;

    /**
     * Variables that are set during completion.
     * Only variables that are contained in the associated form are valid.
     */
    @NotNull
    private Map<String, Object> variables;

  }


  /**
   * Form object.
   *
   * @author externer.dl.horn
   */
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class FormTO {

    /**
     * Key of the form.
     */
    @NotBlank
    private String key;

    /**
     * description of the form.
     */
    private String description;

    /**
     * authorized groups.
     */
    private String authorizedGroups;

    /**
     * Buttons of the form.
     */
    private ButtonsTO buttons;

    /**
     * Sections of the form including all form fields.
     */
    @Size(min = 1, max = 100)
    @Builder.Default
    private List<GroupTO> groups = new ArrayList<>();
  }

  /**
   * Wrapper object for buttons.
   *
   * @author martin.dietrich
   */
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class ButtonsTO {

    /**
     * Complete button.
     */
    private ButtonTO complete;

    /**
     * Cancel button.
     */
    private ButtonTO cancel;

    /**
     * Status PDF button.
     */
    private ButtonTO statusPdf;

  }

  /**
   * Button object.
   *
   * @author martin.dietrich
   */
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class ButtonTO {

    /**
     * Indicates whether a button should be displayed or not
     */
    private boolean showButton;

    /**
     * Button display text
     */
    private String buttonText;

  }

  /**
   * Group of a from.
   * Includes form fields.
   *
   * @author externer.dl.horn
   */
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class GroupTO {

    /**
     * Label of the group.
     */
    private String label;

    /**
     * Schema of the group.
     * Includes form fields.
     */
    @Builder.Default
    private List<FormFieldTO> schema = new ArrayList<>();

  }

  /**
   * FormField object.
   * Contains configuration for the UI
   *
   * @author externer.dl.horn
   */
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class FormFieldTO {

    /**
     * Type of the form field.
     */
    @NotBlank
    private String type;

    /**
     * Key of the form field.
     */
    @NotBlank
    private String key;

    /**
     * Default value that is used if no value is present.
     */
    private String defaultValue;

    /**
     * Default value field is used to fill a value from the data.
     */
    private String defaultValueField;

    /**
     * Label of the field.
     */
    private String label;

    /**
     * Prepend icon for the input field.
     */
    private String prependIcon;

    /**
     * Tooltip of the field.
     */
    private String tooltip;

    /**
     * Specifies the exact type of the input field.
     * Relevant for text fields
     */
    private String ext;

    /**
     * Indicates whether it is a multiple selection.
     * Relevant for select fields
     */
    private boolean multiple;

    /**
     * Description of the field.
     */
    private String description;

    /**
     * Ldap groups are relevant for the ldap-input.
     * Restrict the field to the specified groups.
     */
    private String ldapOus;

    /**
     * Height of the image.
     * Relevant for the image field.
     */
    private String imageHeight;

    /**
     * Width of the image.
     * Relevant for the image field.
     */
    private String imageWidth;

    /**
     * Indicates if the field is readonly.
     * Readonly fields are filtered when a form is completed.
     */
    private boolean readonly;

    /**
     * Rows of the textarea.
     */
    private Integer rows;

    /**
     * Width of the field.
     * Between 1 and 12.
     */
    @Builder.Default
    private Integer col = 12;

    /**
     * Items of the select field.
     */
    @Builder.Default
    private List<ItemTO> items = new ArrayList<>();

    /**
     * Rules of the field.
     * Used for validation in the frontend.
     */
    @Builder.Default
    private List<RuleTO> rules = new ArrayList<>();

    @Builder.Default
    private String itemText = "name";

    @Builder.Default
    private String itemValue = "value";

    @Builder.Default
    private Boolean returnObject = false;
  }

  /**
   * Item of a select field.
   *
   * @author externer.dl.horn
   */
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class ItemTO {

    /**
     * Display name of the item.
     */
    private String name;

    /**
     * Value of the item.
     * Used for saving.
     */
    private String value;

  }

  /**
   * Rule of a form field.
   * Used for validation in the frontend.
   *
   * @author externer.dl.horn
   */
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  class RuleTO {

    /**
     * Type of the rule.
     */
    private String type;

    /**
     * Value fo the rule.
     */
    private String value;

    /**
     * Target of the rule.
     * Used for required on validation.
     */
    private String target;

  }

}
