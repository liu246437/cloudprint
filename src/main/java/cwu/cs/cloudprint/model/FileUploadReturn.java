package cwu.cs.cloudprint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadReturn {

    // 本地的临时文件夹
    private String tempPath;

    // hashKey值
    private String cloudKey;
}
