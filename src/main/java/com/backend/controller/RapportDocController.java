package com.backend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.message.response.ResponseMessage;
import com.backend.model.RapportDoc;
import com.backend.model.Analyse;
import com.backend.model.Rapport;
import com.backend.repository.RapportDocRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableHeader;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/dms")
public class RapportDocController {
	
	@Autowired
	RapportDocRepository rapDocRepo;
	
	@GetMapping("/getAllRapports")
	@Secured({"ROLE_PL"})
	public List<RapportDoc> getAllRapports() {
		System.out.println("Get all RapportDocs...");
	    List<RapportDoc> rapportDocs = new ArrayList<>();
	    rapDocRepo.findAll().forEach(rapportDocs::add);
	    return rapportDocs;
	}
	
	@PostMapping("/getByDate")
	@Secured({"ROLE_PATIENT"})
	public List<RapportDoc> getByDate(@Valid @RequestBody long idPatient, String dateCreation ) {
		System.out.println("Get RapportDocs By DateCreation...");
	    List<RapportDoc> rapportDocs = new ArrayList<>();
	    rapDocRepo.findByDateCreationAndIdPatient(dateCreation, idPatient).forEach(rapportDocs::add);
	    return rapportDocs;
	}
	
	@PostMapping("/getById")
	@Secured({"ROLE_PATIENT"})
	public List<RapportDoc> getById(@Valid @RequestBody long idPatient) {
		System.out.println("Get all RapportDocs...");
	    List<RapportDoc> rapportDocs = new ArrayList<>();
	    rapDocRepo.findByIdPatient(idPatient).forEach(rapportDocs::add);
	    return rapportDocs;
	}
	
	@PostMapping("/getByFullname")
	@Secured({"ROLE_PL"})
	public List<RapportDoc> getByFullname(@Valid @RequestBody String fullnamePatient) {
		System.out.println("Get all RapportDocs...");
	    List<RapportDoc> rapportDocs = new ArrayList<>();
	    rapDocRepo.findByfullnamePatient(fullnamePatient).forEach(rapportDocs::add);
	    return rapportDocs;
	}
	
	@DeleteMapping("/{id}")
	@Secured({"ROLE_PL"})
	public ResponseEntity<?> deleteRapport(@PathVariable("id") long idRapport) {
	    System.out.println("Delete Rapport with ID = " + idRapport + "...");
	    rapDocRepo.deleteById(idRapport);
	    return new ResponseEntity<>(new ResponseMessage("Rapport has been deleted!"), HttpStatus.OK);
	}
	
	@PostMapping("/generateDoc")
	@Secured({"ROLE_PL"})
	public ResponseEntity<?> generateDoc(@RequestBody Rapport rapport) throws IOException {
		Document document = new Document();
		String fileName = "Rapport_" + rapport.getNamePatient().toString() + "_" + rapport.getIdRapport().toString();
		try {
			
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
			document.open();
			
			//Add Image, Scale to new height and new width of image
			Image image = Image.getInstance(".\\src\\main\\resources\\static\\logo.png");
			image.scaleAbsolute(50, 50);
		    document.add(image);
		    
		    // add a couple of blank lines
		    document.add(new Paragraph(new Phrase(Chunk.NEWLINE)));
		    
		    // add Paragraph as Title
		    Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLUE);
		    Chunk titleChunk = new Chunk("RÉSULTATS DES ANALYSES", fontTitle);
		    Paragraph title = new Paragraph(new Phrase(titleChunk));
		    title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			
			// add a couple of blank lines
		    document.add(new Paragraph(new Phrase(Chunk.NEWLINE)));
			
		    // creating Table for information about the report and patient
			PdfPTable tableInfo = new PdfPTable(2);
			tableInfo.setWidthPercentage(100); //Width 100%
			tableInfo.setSpacingBefore(10f); //Space before table
			tableInfo.setSpacingAfter(10f); //Space after table
			PdfPCell cell1 = new PdfPCell(new Paragraph("EFFECTUEES LE : "+rapport.getDateAnalyse().toString()));
			cell1.setFixedHeight(30f);
			PdfPCell cell2 = new PdfPCell(new Paragraph("DOSSIER N° : "+rapport.getIdRapport().toString()));
			cell2.setFixedHeight(30f);
			PdfPCell cell3 = new PdfPCell(new Paragraph("POUR : "+rapport.getNamePatient().toString()));
			cell3.setFixedHeight(30f);
			PdfPCell cell4 = new PdfPCell(new Paragraph("DOCTEUR : "+rapport.getNomDocteur().toString()));
			cell4.setFixedHeight(30f);
			tableInfo.addCell(cell1);
			tableInfo.addCell(cell2);
			tableInfo.addCell(cell3);
			tableInfo.addCell(cell4);
	        document.add(tableInfo);
			
	        document.add(new Paragraph(new Phrase(Chunk.NEWLINE)));
	        
	    	// creating Table for Analyses
	        PdfPTable tableAnalyse = new PdfPTable(3);
	        tableAnalyse.setWidthPercentage(100); //Width 100%
	        tableAnalyse.setSpacingBefore(10f); //Space before table
	        tableAnalyse.setSpacingAfter(10f); //Space after table
	        tableAnalyse.addCell("Analyse Demandé");
	        tableAnalyse.addCell("Résultat");
	        tableAnalyse.addCell("Valeur Normal");
	        tableAnalyse.setHeaderRows(1);
	        rapport.getAnalyseTab().forEach(item -> {
	        	PdfPCell cel1 = new PdfPCell(new Paragraph(item.get("analyseDemande").toString()));
	        	PdfPCell cel2 = new PdfPCell(new Paragraph(item.get("resultat").toString()));
	        	PdfPCell cel3 = new PdfPCell(new Paragraph(item.get("valNormal").toString()));
	        	tableAnalyse.addCell(cel1);
	        	tableAnalyse.addCell(cel2);
	        	tableAnalyse.addCell(cel3);
	        });
	        document.add(tableAnalyse);
	        writer.close();
			document.close();			
		} catch (DocumentException e) {
			e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			String fileN = StringUtils.cleanPath(fileName+".pdf");
			File file = new File(fileN);
			String path = file.getAbsolutePath();
//			byte[] bytesArray = new byte[(int) file.length()];
//			FileInputStream fis = new FileInputStream(file);
//			fis.read(bytesArray); //read file into bytes[]
	        RapportDoc doc = new RapportDoc(rapport.getIdRapport(), rapport.getIdPatient(), rapport.getNamePatient(), fileN,
	        		".pdf", path, rapport.getDateAnalyse());
	        rapDocRepo.save(doc);
//			fis.close();
		}
		return new ResponseEntity<>(new ResponseMessage("PDF has been created"), HttpStatus.OK);
	}
	
//	@PostMapping("/download")
//	public ResponseEntity<?> download(@RequestBody long idRapport) {
//	    List<RapportDoc> rapportDocs = new ArrayList<>();
//	    RapportDoc docInfo = rapDocRepo.findByIdRapport(idRapport);
//	    System.out.println(docInfo);
//	    try {
//			FileOutputStream fos = new FileOutputStream("d:\\" + docInfo.getNameFile());
//			fos.write(docInfo.getData());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    
//	    return new ResponseEntity<>(new ResponseMessage("PDF has been download"), HttpStatus.OK);
//	}

}
