/*
 * stack_lite_Java API
 * stack_lite_Java API
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.ObjectId;
import org.openapitools.client.model.Review;

/**
 * Movie
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-07-16T00:24:13.351142+01:00[Africa/Lagos]")
public class Movie {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private ObjectId id;

  public static final String SERIALIZED_NAME_IMDB_ID = "imdbId";
  @SerializedName(SERIALIZED_NAME_IMDB_ID)
  private String imdbId;

  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_RELEASE_DATE = "releaseDate";
  @SerializedName(SERIALIZED_NAME_RELEASE_DATE)
  private String releaseDate;

  public static final String SERIALIZED_NAME_TRAILER_LINK = "trailerLink";
  @SerializedName(SERIALIZED_NAME_TRAILER_LINK)
  private String trailerLink;

  public static final String SERIALIZED_NAME_POSTER = "poster";
  @SerializedName(SERIALIZED_NAME_POSTER)
  private String poster;

  public static final String SERIALIZED_NAME_GENRES = "genres";
  @SerializedName(SERIALIZED_NAME_GENRES)
  private List<String> genres = null;

  public static final String SERIALIZED_NAME_BACKDROPS = "backdrops";
  @SerializedName(SERIALIZED_NAME_BACKDROPS)
  private List<String> backdrops = null;

  public static final String SERIALIZED_NAME_REVIEW_IDS = "reviewIds";
  @SerializedName(SERIALIZED_NAME_REVIEW_IDS)
  private List<Review> reviewIds = null;


  public Movie id(ObjectId id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ObjectId getId() {
    return id;
  }


  public void setId(ObjectId id) {
    this.id = id;
  }


  public Movie imdbId(String imdbId) {
    
    this.imdbId = imdbId;
    return this;
  }

   /**
   * Get imdbId
   * @return imdbId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getImdbId() {
    return imdbId;
  }


  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }


  public Movie title(String title) {
    
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getTitle() {
    return title;
  }


  public void setTitle(String title) {
    this.title = title;
  }


  public Movie releaseDate(String releaseDate) {
    
    this.releaseDate = releaseDate;
    return this;
  }

   /**
   * Get releaseDate
   * @return releaseDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getReleaseDate() {
    return releaseDate;
  }


  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }


  public Movie trailerLink(String trailerLink) {
    
    this.trailerLink = trailerLink;
    return this;
  }

   /**
   * Get trailerLink
   * @return trailerLink
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getTrailerLink() {
    return trailerLink;
  }


  public void setTrailerLink(String trailerLink) {
    this.trailerLink = trailerLink;
  }


  public Movie poster(String poster) {
    
    this.poster = poster;
    return this;
  }

   /**
   * Get poster
   * @return poster
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPoster() {
    return poster;
  }


  public void setPoster(String poster) {
    this.poster = poster;
  }


  public Movie genres(List<String> genres) {
    
    this.genres = genres;
    return this;
  }

  public Movie addGenresItem(String genresItem) {
    if (this.genres == null) {
      this.genres = new ArrayList<String>();
    }
    this.genres.add(genresItem);
    return this;
  }

   /**
   * Get genres
   * @return genres
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getGenres() {
    return genres;
  }


  public void setGenres(List<String> genres) {
    this.genres = genres;
  }


  public Movie backdrops(List<String> backdrops) {
    
    this.backdrops = backdrops;
    return this;
  }

  public Movie addBackdropsItem(String backdropsItem) {
    if (this.backdrops == null) {
      this.backdrops = new ArrayList<String>();
    }
    this.backdrops.add(backdropsItem);
    return this;
  }

   /**
   * Get backdrops
   * @return backdrops
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getBackdrops() {
    return backdrops;
  }


  public void setBackdrops(List<String> backdrops) {
    this.backdrops = backdrops;
  }


  public Movie reviewIds(List<Review> reviewIds) {
    
    this.reviewIds = reviewIds;
    return this;
  }

  public Movie addReviewIdsItem(Review reviewIdsItem) {
    if (this.reviewIds == null) {
      this.reviewIds = new ArrayList<Review>();
    }
    this.reviewIds.add(reviewIdsItem);
    return this;
  }

   /**
   * Get reviewIds
   * @return reviewIds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Review> getReviewIds() {
    return reviewIds;
  }


  public void setReviewIds(List<Review> reviewIds) {
    this.reviewIds = reviewIds;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Movie movie = (Movie) o;
    return Objects.equals(this.id, movie.id) &&
        Objects.equals(this.imdbId, movie.imdbId) &&
        Objects.equals(this.title, movie.title) &&
        Objects.equals(this.releaseDate, movie.releaseDate) &&
        Objects.equals(this.trailerLink, movie.trailerLink) &&
        Objects.equals(this.poster, movie.poster) &&
        Objects.equals(this.genres, movie.genres) &&
        Objects.equals(this.backdrops, movie.backdrops) &&
        Objects.equals(this.reviewIds, movie.reviewIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, imdbId, title, releaseDate, trailerLink, poster, genres, backdrops, reviewIds);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Movie {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    imdbId: ").append(toIndentedString(imdbId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    releaseDate: ").append(toIndentedString(releaseDate)).append("\n");
    sb.append("    trailerLink: ").append(toIndentedString(trailerLink)).append("\n");
    sb.append("    poster: ").append(toIndentedString(poster)).append("\n");
    sb.append("    genres: ").append(toIndentedString(genres)).append("\n");
    sb.append("    backdrops: ").append(toIndentedString(backdrops)).append("\n");
    sb.append("    reviewIds: ").append(toIndentedString(reviewIds)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
