package com.assignment.image.MagicImages.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.assignment.image.MagicImages.Model.Photo;

@RestController
@RequestMapping("/api/v1/photos")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PhotoResource {

	@Value("${PhotoApp.ImagURL}")
	private String photoResourceUrl;

	@Value("${PhotoApp.MaxSeqNum}")
	private int MaxSeqNum;

	@Value("${PhotoApp.DefalutNumPerPage}")
	private int NumPerPage;

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
			// TODO: get logic to get Content-Length , Etag to determin from
			// ResponseHeader of HTTPClientException, then determine if contine
			// to fetch next id or not.
		}
		if (response != null) {
			photo = response.getBody();

			logger.info("retrieve Photo:" + photo.getId() + " " + photo.getName() + " time:" + photo.getCreatedAt()
					+ " ImageUurl:" + photo.getImageUrl());
		}
		return response;

	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity getPhotos() {
		logger.info("photow  invoked: ");

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

	@RequestMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity byId(@PathVariable("id") long id) {
		logger.info("photo  byId() invoked: " + id);
		return getPhoto(id);
	}

	@RequestMapping(value = "/pagnation", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity byPageNum(@RequestParam(required = false, value = "offset") int offset,
			@RequestParam(value = "limit") int limit) {
		logger.info("photo  byPageNum() invoked: offset:" + offset + " limit:" + limit);
		return getPhotoPage(limit, offset);
	}

	private ResponseEntity getPhotoPage(int limit, int offset) {
		if (listPhotos == null || listPhotos.size() == 0) {
			getAllPhotos();
		}
		if (offset <= 0) offset = 1;
		if (limit <= 0)	limit = NumPerPage;
		if (offset >= listPhotos.size() / limit + 1)
			offset = listPhotos.size() / limit + 1;
		

		List list = listPhotos.stream().skip(offset).limit(limit).collect(Collectors.toList());
		logger.info("offset:" + offset + " limit:" + limit + " list.siz:" + list.size());

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
