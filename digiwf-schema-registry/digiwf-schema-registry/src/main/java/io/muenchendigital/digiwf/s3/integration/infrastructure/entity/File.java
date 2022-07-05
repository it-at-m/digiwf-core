package io.muenchendigital.digiwf.s3.integration.infrastructure.entity;

import io.muenchendigital.digiwf.s3.integration.infrastructure.repository.FileRepository;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(indexes = {
        @Index(
                name = "index_path_to_file",
                columnList = "path_to_file"
        ),
        @Index(
                name = "index_end_of_life",
                columnList = "end_of_life"
        )
})
public class File extends BaseEntity {

    @Column(name = "path_to_file", nullable = false, unique = true, length = FileRepository.LENGTH_PATH_TO_FILE)
    private String pathToFile;

    @Column(name = "end_of_life")
    private LocalDate endOfLife;

}
