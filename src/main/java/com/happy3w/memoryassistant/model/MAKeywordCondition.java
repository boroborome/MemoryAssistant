/**
 *
 */
package com.happy3w.memoryassistant.model;

import com.happy3w.footstone.svc.IDataCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MAKeywordCondition implements IDataCondition<MAKeyword> {
    /**
     * A test contains '%'
     */
    private String keywordLike;
}
