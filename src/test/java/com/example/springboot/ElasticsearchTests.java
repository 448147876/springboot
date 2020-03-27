package com.example.springboot;

import com.example.springboot.entity.EsBlog;
import com.example.springboot.entity.EsBlogRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTests {
    @Autowired
    private EsBlogRepository esBlogRepository;

    @Before
    public void initData() {
        esBlogRepository.deleteAll();
        //测试数据
        esBlogRepository.save(new EsBlog("劝学诗"," 颜真卿的劝学诗","黑发不知勤学早，白首方悔读书迟。"));
        esBlogRepository.save(new EsBlog("冬夜读书示子聿","  陆游的冬夜读书示子聿","纸上得来终觉浅，绝知此事要躬行。"));
        esBlogRepository.save(new EsBlog("泊秦淮","  杜牧的泊秦淮","商女不知亡国恨，隔江犹唱后庭花。"));
    }

    @Test
    public void testFind() {
        Pageable pageable=new PageRequest(0,2);
        String title="诗";
        String summary="劝学诗";
        String content="勤学";
        Page<EsBlog> page = esBlogRepository.findByTitleOrSummaryOrContent(title, summary, content, pageable);
        for (EsBlog item:page.getContent()){
            System.out.println(item.getTitle());
        }
    }


}
