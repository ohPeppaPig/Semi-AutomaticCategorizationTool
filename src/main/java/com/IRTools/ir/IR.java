package com.IRTools.ir;

import com.IRTools.document.SimilarityMatrix;
import com.IRTools.document.SingleLink;
import com.IRTools.document.TextDataset;

public class IR {

    public static void compute(TextDataset textDataset, String modelType) {

        try {
            Class modelTypeClass = Class.forName(modelType);
            IRModel irModel = (IRModel) modelTypeClass.newInstance();
            // 计算得到IR候选追踪矩阵
            SimilarityMatrix similarityMatrix = irModel.Compute(textDataset.getSourceCollection(),
                    textDataset.getTargetCollection());
            // TODO 2018-8-26
//			for (SingleLink link : similarityMatrix.allLinks()) {
//				System.out.println(IR.class.getName()+"/compute/line29: "+link.getSourceArtifactId()+" "+link.getTargetArtifactId()+" "+link.getScore());
//			}


            for (SingleLink link : similarityMatrix.allLinks()) {
                String source = link.getSourceArtifactId();
                String target = link.getTargetArtifactId();

                System.out.println(source + "  " + target + "  " + link.getScore());

                similarityMatrix.addLink(source, target, link.getScore());
//                if (textDataset.getRtm().sourceArtifactsIds().contains(source) && textDataset.getRtm().targetArtifactsIds().contains(target)) {
//                    similarityMatrix.addLink(source, target, link.getScore());
//                }
            }

            /*
             * add a code line
             */


        } catch (ClassNotFoundException e) {
            System.out.println("No such IR model exists");
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static double computeAvg(TextDataset textDataset, String modelType) {

        try {
            Class modelTypeClass = Class.forName(modelType);
            IRModel irModel = (IRModel) modelTypeClass.newInstance();
            // 计算得到IR候选追踪矩阵
            SimilarityMatrix similarityMatrix = irModel.Compute(textDataset.getSourceCollection(),
                    textDataset.getTargetCollection());
            // TODO 2018-8-26
//			for (SingleLink link : similarityMatrix.allLinks()) {
//				System.out.println(IR.class.getName()+"/compute/line29: "+link.getSourceArtifactId()+" "+link.getTargetArtifactId()+" "+link.getScore());
//			}

            double sum = 0;
            for (SingleLink link : similarityMatrix.allLinks()) {
                sum +=link.getScore();
            }

            int size = similarityMatrix.allLinks().size();
//            System.out.println(sum+":"+size);
            // 返回平均数
            return sum/size;



        } catch (ClassNotFoundException e) {
            System.out.println("No such IR model exists");
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;

    }



}
