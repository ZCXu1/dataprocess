package com.xzc;

import com.alibaba.fastjson.JSON;
import com.xzc.entity.Entity;
import com.xzc.entity.Similarity;
import com.xzc.entity.Vector;
import com.xzc.mapper.EntityMapper;
import com.xzc.mapper.SimilarityMapper;
import com.xzc.mapper.VectorMapper;
import com.xzc.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void addEntities(String filepath) throws IOException {
        File file = new File(filepath);
        FileReader fr = null;
        List<String> names = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while ((line = br.readLine()) != null){
                int space_idx = line.indexOf(' ');
                if (space_idx != -1){
                    names.add(line.substring(0,space_idx));
                    ids.add(Integer.parseInt(line.substring(space_idx+1)));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fr.close();
        }

        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            EntityMapper mapper = sqlSession.getMapper(EntityMapper.class);
            int n = ids.size();
            for (int i = 0; i < n; i++) {
                int res = mapper.addEntity(new Entity(ids.get(i), names.get(i)));
            }
            sqlSession.commit();
        }
    }
    public static void addSimilarity(String filepath) throws IOException {
        File file = new File(filepath);
        FileReader fr = null;
        List<String> similarities = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        List<String> l = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null){
                l = Arrays.asList(line.substring(1, line.length() - 1).split(","));
                similarities.add(l.toString());
                ids.add(Integer.parseInt(l.get(0)));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fr.close();
        }

        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            SimilarityMapper mapper = sqlSession.getMapper(SimilarityMapper.class);
            int n = ids.size();
            for (int i = 0; i < n; i++) {
                int res = mapper.addSimilarity(new Similarity(ids.get(i), similarities.get(i)));
            }
            sqlSession.commit();
        }
    }
    public static void addVector(String filepath) throws IOException {
        File file = new File(filepath);
        FileReader fr = null;
        List<String> vectors = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        List<String> l = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            l = JSON.parseArray(line,String.class);
            int n = l.size();
            for (int i = 0; i < n; i++) {
                ids.add(i);
                vectors.add(l.get(i));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fr.close();
        }

        try (SqlSession sqlSession = MybatisUtil.getSqlSession()){
            VectorMapper mapper = sqlSession.getMapper(VectorMapper.class);
            int n = ids.size();
            for (int i = 0; i < n; i++) {
                int res = mapper.addVector(new Vector(ids.get(i), vectors.get(i)));
            }
            sqlSession.commit();
        }
    }

    public static void main(String[] args) throws IOException {
        addVector("src/main/resources/vector.txt");
    }
}
