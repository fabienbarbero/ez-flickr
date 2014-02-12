/*
 * Copyright (C) 2011 by Fabien Barbero
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.flickr.api.services;

import com.flickr.api.FlickrService;
import java.util.List;
import com.flickr.api.CommandArguments;
import com.flickr.api.FlickrServiceException;
import com.flickr.api.OAuthHandler;
import com.flickr.api.entities.BaseUser;
import com.flickr.api.entities.Comment;
import com.flickr.api.entities.CommentResponse;
import com.flickr.api.entities.CommentsResponse;
import com.flickr.api.entities.ExifInfos;
import com.flickr.api.entities.ExifInfosResponse;
import com.flickr.api.entities.License;
import com.flickr.api.entities.LicensesResponse;
import com.flickr.api.entities.Paginated;
import com.flickr.api.entities.PhotosResponse;
import com.flickr.api.entities.Photo;
import com.flickr.api.entities.PhotoPermissions;
import com.flickr.api.entities.PhotoInfos;
import com.flickr.api.entities.PhotoInfosResponse;
import com.flickr.api.entities.PhotoSize;
import com.flickr.api.entities.PhotoSizesResponse;
import com.flickr.api.entities.PhotoTag;
import com.flickr.api.entities.VoidResponse;

/**
 * Service used to access the photos.
 *
 * @author Fabien Barbero
 */
public class PhotosService extends FlickrService {

    public PhotosService(OAuthHandler oauthHandler) {
        super(oauthHandler);
    }

    /**
     * Fetch a list of recent photos from the calling users' contacts.
     *
     * @return The photos
     * @throws FlickrServiceException Error getting the photos
     */
    public Paginated<Photo> getContactsPhotos() throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getContactsPhotos");
        return doGet(args, PhotosResponse.class).getPaginated();
    }

    /**
     * Fetch a list of recent photos from the calling users' contacts.
     *
     * @param count Number of photos to return
     * @param justFriends To only show photos from friends and family (excluding regular contacts).
     * @param singlePhoto Only fetch one photo (the latest) per contact, instead of all photos in chronological order.
     * @param includeSelf To include photos from the user specified by user_id.
     * @return The photos
     * @throws FlickrServiceException Error getting the photos
     */
    public Paginated<Photo> getContactsPhotos(int count, boolean justFriends, boolean singlePhoto, boolean includeSelf) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getContactsPhotos");
        args.addParam("count", count);
        args.addParam("just_friends", justFriends);
        args.addParam("single_photo", singlePhoto);
        args.addParam("include_self", includeSelf);
        return doGet(args, PhotosResponse.class).getPaginated();
    }

    /**
     * Fetch a list of recent public photos from a users' contacts.
     *
     * @param user The user to fetch photos for
     * @return The photos
     * @throws FlickrServiceException Error getting the photos
     */
    public Paginated<Photo> getContactsPublicPhotos(BaseUser user) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getContactsPublicPhotos");
        args.addParam("user_id", user.getId());
        return doGet(args, PhotosResponse.class).getPaginated();
    }

    /**
     * Fetch a list of recent public photos from a users' contacts.
     *
     * @param user The user to fetch photos for
     * @param count Number of photos to return
     * @param justFriends To only show photos from friends and family (excluding regular contacts).
     * @param singlePhoto Only fetch one photo (the latest) per contact, instead of all photos in chronological order.
     * @param includeSelf To include photos from the user specified by user_id.
     * @return The photos
     * @throws FlickrServiceException Error getting the photos
     */
    public Paginated<Photo> getContactsPublicPhotos(BaseUser user, int count, boolean justFriends, boolean singlePhoto, boolean includeSelf) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getContactsPublicPhotos");
        args.addParam("user_id", user.getId());
        args.addParam("count", count);
        args.addParam("just_friends", justFriends);
        args.addParam("single_photo", singlePhoto);
        args.addParam("include_self", includeSelf);
        return doGet(args, PhotosResponse.class).getPaginated();
    }

    /**
     * Get information about a photo. The calling user must have permission to view the photo.
     *
     * @param photo The photo
     * @return The photo informations
     * @throws FlickrServiceException Error getting the informations
     */
    public PhotoInfos getInfos(Photo photo) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getInfo");
        args.addParam("photo_id", photo.getId());
        return doGet(args, PhotoInfosResponse.class).getInfos();
    }

    /**
     * Get permissions for a photo.
     *
     * @param photo The photo
     * @return The photo permissions
     * @throws FlickrServiceException Error getting the permissions
     */
    public PhotoPermissions getPermissions(Photo photo) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getPerms");
        args.addParam("photo_id", photo.getId());
        return doGet(args, PhotoPermissions.class);
    }

    /**
     * Returns a list of the latest public photos uploaded to flickr.
     *
     * @param perPage Number of photos to return per page. The maximum allowed value is 500.
     * @param page The page of results to return
     * @return The recent photos
     * @throws FlickrServiceException Error getting the photos
     */
    public Paginated<Photo> getRecent(int perPage, int page) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getRecent");
        args.addParam("per_page", perPage);
        args.addParam("page", page);
        Paginated<Photo> photos = doGet(args, PhotosResponse.class).getPaginated();
        return photos;
    }

    /**
     * Returns the available sizes for a photo. The calling user must have permission to view the photo.
     *
     * @param photo The photo
     * @return The photo sizes
     * @throws FlickrServiceException Error getting the sizes
     */
    public List<PhotoSize> getSizes(Photo photo) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getSizes");
        args.addParam("photo_id", photo.getId());
        List<PhotoSize> sizes = doGet(args, PhotoSizesResponse.class).getList();
        return sizes;
    }

    /**
     * Return a list of your photos that have been recently created or which have been recently modified. Recently
     * modified may mean that the photo's metadata (title, description, tags) may have been changed or a comment has
     * been added (or just modified somehow :-)
     *
     * @param perPage Number of photos to return per page. The maximum allowed value is 500.
     * @param page The page of results to return
     * @return The photos
     * @throws FlickrServiceException Error getting the photos
     */
    public Paginated<Photo> getRecentlyUpdated(int perPage, int page) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.recentlyUpdated");
        args.addParam("per_page", perPage);
        args.addParam("page", page);
        args.addParam("extras", "date_upload");
        args.addParam("min_date", "10000");
        Paginated<Photo> photos = doGet(args, PhotosResponse.class).getPaginated();
        return photos;
    }

    /**
     * Retrieves a list of EXIF/TIFF/GPS tags for a given photo. The calling user must have permission to view the
     * photo.
     *
     * @param photo The photo
     * @return The exif informations
     * @throws FlickrServiceException Error getting the exif informations
     */
    public ExifInfos getExif(Photo photo) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.getExif");
        args.addParam("photo_id", photo.getId());
        return doGet(args, ExifInfosResponse.class).getExifInfos();
    }

    /**
     * Fetches a list of available photo licenses for Flickr.
     *
     * @return The licenses
     * @throws FlickrServiceException Error getting the licenses
     */
    public List<License> getLicenses() throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.licenses.getInfo");
        return doGet(args, LicensesResponse.class).getList();
    }

    /**
     * Returns the comments for a photo
     *
     * @param photo The photo
     * @return The comments
     * @throws FlickrServiceException Error getting the comments
     */
    public List<Comment> getComments(Photo photo) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.comments.getList");
        args.addParam("photo_id", photo.getId());
        return doGet(args, CommentsResponse.class).getList();
    }

    /**
     * Delete a photo from flickr
     *
     * @param photo The photo to delete
     * @throws FlickrServiceException Error deleting the photo
     */
    public void deletePhoto(Photo photo) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.delete");
        args.addParam("photo_id", photo.getId());
        doPost(args, VoidResponse.class);
    }

    /**
     * Set the tags for a photo.
     *
     * @param photo The photo to set the tags
     * @param tags The tags list
     * @throws FlickrServiceException Error setting the tags
     */
    public void setTags(Photo photo, String... tags) throws FlickrServiceException {
        StringBuilder tagsBuilder = new StringBuilder();
        for (String tag : tags) {
            if (tag.contains(" ")) {
                tagsBuilder.append("\"").append(tag).append("\"");
            } else {
                tagsBuilder.append(tag);
            }
            tagsBuilder.append(" ");
        }

        CommandArguments args = new CommandArguments("flickr.photos.setTag");
        args.addParam("photo_id", photo.getId());
        args.addParam("tags", tagsBuilder);
        doPost(args, VoidResponse.class);
    }

    /**
     * Remove a tag on a photo
     *
     * @param tag The tag to remove
     * @throws FlickrServiceException Error removing tag
     */
    public void removeTag(PhotoTag tag) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.removeTag");
        args.addParam("tag_id", tag.getId());
        doPost(args, VoidResponse.class);
    }

    /**
     * Set the meta information for a photo.
     *
     * @param photo The photo to modify
     * @param title The new photo title
     * @param description The new photo description
     * @throws FlickrServiceException Error updating the photo meta
     */
    public void setPhotoMeta(Photo photo, String title, String description) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.setMeta");
        args.addParam("photo_id", photo.getId());
        args.addParam("title", title);
        args.addParam("description", description);
        doPost(args, VoidResponse.class);
    }

    /**
     * Set permissions for a photo.
     *
     * @param photo The photo to modify the permissions
     * @param isPublic true to set the photo public, false otherwise
     * @param isFriend true to set the photo accessible for friends, false otherwise
     * @param isFamily true to set the photo accessible for family, false otherwise
     * @param commentsPerms Comments permissions
     * @param addMetaPerms Meta add permissions
     * @throws FlickrServiceException Error setting the permissions
     */
    public void setPhotoPermissions(Photo photo, boolean isPublic, boolean isFriend, boolean isFamily, Permission commentsPerms, Permission addMetaPerms) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.setPerms");
        args.addParam("photo_id", photo.getId());
        args.addParam("is_public", isPublic);
        args.addParam("is_friend", isFriend);
        args.addParam("is_family", isFamily);
        args.addParam("perm_comment", commentsPerms.value);
        args.addParam("perm_addmeta", addMetaPerms.value);
        doPost(args, VoidResponse.class);
    }

    /**
     * Add a comment to a photo
     *
     * @param photo The photo
     * @param text The comment to add
     * @return The comment
     * @throws FlickrServiceException Error adding the comment
     */
    public Comment addPhotoComment(Photo photo, String text) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.comments.addComment");
        args.addParam("photo_id", photo.getId());
        args.addParam("comment_text", text);
        return doPost(args, CommentResponse.class).getComment();
    }

    /**
     * Delete a comment
     *
     * @param comment The comment to delete
     * @throws FlickrServiceException Error deleting the comment
     */
    public void deleteComment(Comment comment) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.comments.deleteComment");
        args.addParam("comment_id", comment.getId());
        doPost(args, VoidResponse.class);
    }

    /**
     * Edit the text of a comment as the currently authenticated user.
     *
     * @param comment The comment to update
     * @param text The new comment text
     * @throws FlickrServiceException Error editing the comment
     */
    public void editComment(Comment comment, String text) throws FlickrServiceException {
        CommandArguments args = new CommandArguments("flickr.photos.comments.editComment");
        args.addParam("comment_id", comment.getId());
        args.addParam("comment_text", text);
        doPost(args, VoidResponse.class);
    }

    public enum Permission {

        JUST_OWNER(0),
        FRIENDS_FAMILY(1),
        CONTACTS(2),
        EVERYBODY(3);

        private final int value;

        private Permission(int value) {
            this.value = value;
        }
    }

}
