package com.assignment.image.MagicImages.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.assignment.image.MagicImages.Photo;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PhotoResource {

	private static final String photoResourceUrl = "http://5ad8d1c9dc1baa0014c60c51.mockapi.io/api/br/v1/magic";
	private static final int MaxSeqNum = 50;
	private static final int PicsPerPage = 10;

	private List<Photo> listPhotos = new LinkedList<>();

	protected Logger logger = Logger.getLogger(PhotoResource.class.getName());

	private ResponseEntity<Photo> getPhoto(long id) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Photo> response = null;
		Photo photo;
		try {
			response = restTemplate.getForEntity(photoResourceUrl + "/" + id, Photo.class);
			photo = response.getBody();
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).info("Id: " + id + ", " + e.fillInStackTrace().toString());
		}
		if (response != null) {
			photo = response.getBody();

			logger.info("retrieve Photo:" + photo.getId() + " " + photo.getName() + " time:" + photo.getCreatedAt()
					+ " ImageUurl:" + photo.getImageUrl());
		}
		return response;

	}

	@RequestMapping(value = "/photos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity getPhotos() {
		logger.info("photow  invoked: ");

		// private List<Photo> listPhotos = new LinkedList<>();
		for (long i = 1; i <= MaxSeqNum; i++) {
			ResponseEntity<Photo> result = getPhoto(i);
			if (result != null && result.getBody() != null) {
				listPhotos.add(result.getBody());
			}
		}

		if (listPhotos.size() > 0)
			return ResponseEntity.ok().body(listPhotos);
		else
			return ResponseEntity.noContent().build();

	}

	@RequestMapping("/photos/{id}")
	public ResponseEntity byId(@PathVariable("id") long id) {
		logger.info("photo  byId() invoked: " + id);
		return getPhoto(id);
	}

	@RequestMapping("/photos/pages/{pagenum}")
	public ResponseEntity byPageNum(@PathVariable("pagenum") int pagenum) {
		logger.info("photo  byPageNum() invoked: " + pagenum);
		return getPhotoPage(pagenum);
	}

	private ResponseEntity getPhotoPage(int pagenum) {
		if (listPhotos == null || listPhotos.size() == 0) {
			// listPhotos = getAllPhotos();
			getAllPhotos();
		}
		if (pagenum <= 0)
			pagenum = 1;
		if (pagenum >= listPhotos.size() / PicsPerPage + 1)
			pagenum = listPhotos.size() / PicsPerPage + 1;
		int startIdx = (pagenum - 1) * PicsPerPage + 1;
		int endIdx = pagenum * PicsPerPage;

		// List list = Stream.of(listPhotos).skip(startIdx -1).limit(PicsPerPage).collect(Collectors.toList());
		List list = Stream.of(listPhotos).skip(startIdx - 1).collect(Collectors.toList());
		logger.info("startIdx:" + startIdx + " endIdx:" + endIdx + " list.siz:" + list.size());
		if (list.size() > 0)
			return ResponseEntity.ok().body(list);
		else
			return ResponseEntity.noContent().build();
	}

	private List getAllPhotos() {
		logger.info("getAllPhotos() invoked: ");
		for (long i = 1; i <= MaxSeqNum; i++) {
			ResponseEntity<Photo> result = getPhoto(i);
			if (result != null && result.getBody() != null) {
				listPhotos.add(result.getBody());
			}
		}
		logger.info("getAllPhotos() invoked: " + " listPhotos size:" + listPhotos.size());
		return listPhotos;
	}

}
