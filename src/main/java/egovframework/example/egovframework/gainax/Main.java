package egovframework.example.egovframework.gainax;

import com.vividsolutions.jts.geom.GeometryFactory;
import egovframework.example.cmmn.service.impl.EgovFileMngUtil;
import egovframework.example.cmmn.vo.FileVO;
import egovframework.example.cmmn.web.NlipController;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.geotools.data.*;
import org.geotools.data.shapefile.ShapefileDataStore;

import org.geotools.data.shapefile.shp.ShapefileReader;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@Controller
public class Main extends NlipController {
    /*
  	 * 파일관리 유틸 서비스를 사용하기 위한 변수
  	 */
    @Resource(name="EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    private SimpleFeatureSource featureSource;
    @RequestMapping(value = "/index.do")
    public String selectIndex(final HttpServletRequest request, ModelMap model) throws Exception {
//        File file = new File("example.shp");
//        Map<String, Object> map = new HashMap<>();
//        map.put("url", file.toURI().toURL());
//
//        DataStore dataStore = DataStoreFinder.getDataStore(map);
//        String typeName = dataStore.getTypeNames()[0];
//
//        FeatureSource<SimpleFeatureType, SimpleFeature> source =
//                dataStore.getFeatureSource(typeName);
//        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")
//
//        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
//        try (FeatureIterator<SimpleFeature> features = collection.features()) {
//            while (features.hasNext()) {
//                SimpleFeature feature = features.next();
//                System.out.print(feature.getID());
//                System.out.print(": ");
//                System.out.println(feature.getDefaultGeometryProperty().getValue());
//            }
//        }

        return "Main/index";
    }
    @RequestMapping(value = "/shpSave.do")
    @ResponseBody
    public Object  ShpSave(final HttpServletRequest request, ModelMap model,MultipartHttpServletRequest multiRequest) throws Exception {
        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        List<FileVO> result = null;
        	result = fileUtil.parseFileInf(files, "TN_FAQ_BBS_", 0, "", "","","");
//        final Map<String, MultipartFile> files = multiRequest.getfile();
//        String realFolder = "d:/upload2/";
//        String originFileName  = files.get(0).getOriginalFilename();
//
//        String saveFileName = originFileName;
//
//        String savePath = realFolder + saveFileName;
//
//        long fileSize = files.get(0).getSize(); // 파일 사이즈



//        files.get(0).transferTo(new File(savePath)); // 파일 저장
//
//        File file = new File(savePath);

        Map<String, Object> map = new HashMap<>();
//        map.put("url", file.toURI().toURL());

        DataStore dataStore = DataStoreFinder.getDataStore(map);
        String typeName = dataStore.getTypeNames()[0];

        FeatureSource<SimpleFeatureType, SimpleFeature> source =
                dataStore.getFeatureSource(typeName);
        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")

        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
        try (FeatureIterator<SimpleFeature> features = collection.features()) {
            while (features.hasNext()) {
                SimpleFeature feature = features.next();
                System.out.print(feature.getID());
                System.out.print(": ");
                System.out.println(feature.getDefaultGeometryProperty().getValue());

            }
        }

        return "true";
    }
    @RequestMapping(value = "/shpSave2.do")
    public String  ShpSave2( ModelMap model,MultipartHttpServletRequest multiRequest) throws Exception {
        String rtnUrl = "jsonView";
        final Map<String, MultipartFile> files = multiRequest.getFileMap();
        List<FileVO> result = null;
        result = fileUtil.parseFileInf(files, "TN_FAQ_BBS_", 0, "", "","","");
        String file_url = result.get(0).streCoursNm+"/"+ result.get(0).streFileNm;
        File file = new File("d:/nlipfiles/SIGNGU_TAS.shp");


      //  ShpFiles shpFile = new ShpFiles("d:/nlipfiles/SIGNGU_TAS.shp");
        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        featureSource = store.getFeatureSource();

//        ShapefileDataStore shapefile = new ShapefileDataStore(file.toURI().toURL());
//
//        SimpleFeatureIterator features = shapefile.getFeatureSource().getFeatures().features();
//        SimpleFeature shp;
//        GeometryFactory geometryFactory = new GeometryFactory();
//        ShapefileReader gtlReader = new ShapefileReader(shpFile, false, true, geometryFactory);
//        Map<String, Object> map = new HashMap<>();
//        map.put("url", file.toURI().toURL());
//
//        FileDataStore store = FileDataStoreFinder.getDataStore(file);
//        String typeName = store.getTypeNames()[0];
//        FeatureSource<SimpleFeatureType, SimpleFeature> source =
//                store.getFeatureSource(typeName);
//        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")
//
//        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
//        try (FeatureIterator<SimpleFeature> features = collection.features()) {
//            while (features.hasNext()) {
//                SimpleFeature feature = features.next();
//                System.out.print(feature.getID());
//                System.out.print(": ");
//                System.out.println(feature.getDefaultGeometryProperty().getValue());
//
//            }
//        }

        return rtnUrl;
    }


}

