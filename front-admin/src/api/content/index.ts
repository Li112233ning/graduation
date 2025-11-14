import { http } from "@/utils/http";

// ==================== 稿件管理 ====================

export interface ContentQuery extends BasePageQuery {
  title?: string;
  categoryId?: number;
  contentType?: number;
  platform?: string;
  status?: number;
  auditStatus?: number;
}

export interface ContentDTO {
  contentId?: number;
  title?: string;
  categoryId?: number;
  categoryName?: string;
  imageUrl?: string;
  images?: string;
  tags?: string;
  likesCount?: number;
  commentsCount?: number;
  viewsCount?: number;
  sharesCount?: number;
  contentType?: number;
  platform?: string;
  description?: string;
  status?: number;
  influencerId?: number;
  influencerName?: string;
  auditStatus?: number;
  auditRemark?: string;
  createTime?: Date;
  updateTime?: Date;
}

export interface ContentRequest {
  contentId?: number;
  title: string;
  categoryId?: number;
  imageUrl: string;
  images?: string;
  tags?: string;
  contentType: number;
  platform?: string;
  description?: string;
  status: number;
  influencerId?: number;
}

export const getContentList = (data?: ContentQuery) => {
  return http.request<ResponseData<PageDTO<ContentDTO>>>("get", "/admin/home/content/list", {
    params: data
  });
};

export const getContentDetail = (id: number) => {
  return http.request<ResponseData<ContentDTO>>("get", `/admin/home/content/${id}`);
};

export const addContent = (data: ContentRequest) => {
  return http.request<ResponseData<void>>("post", "/admin/home/content", { data });
};

export const updateContent = (data: ContentRequest) => {
  return http.request<ResponseData<void>>("put", `/admin/home/content/${data.contentId}`, { data });
};

export const deleteContent = (id: number) => {
  return http.request<ResponseData<void>>("delete", `/admin/home/content/${id}`);
};

export const batchDeleteContent = (ids: number[]) => {
  return http.request<ResponseData<void>>("delete", "/admin/home/content/batch", { data: ids });
};

export const auditContent = (id: number, auditStatus: number, auditRemark?: string) => {
  return http.request<ResponseData<void>>("put", `/admin/home/content/${id}/audit`, {
    params: { auditStatus, auditRemark }
  });
};

// ==================== 标签管理 ====================

export interface TagQuery extends BasePageQuery {
  tagName?: string;
  status?: number;
}

export interface TagDTO {
  tagId?: number;
  tagName?: string;
  sortOrder?: number;
  status?: number;
  createTime?: Date;
  updateTime?: Date;
}

export interface TagRequest {
  tagId?: number;
  tagName: string;
  sortOrder?: number;
  status: number;
}

export const getTagList = (data?: TagQuery) => {
  return http.request<PageResult<TagDTO>>("get", "/admin/tag/list", {
    params: data
  });
};

export const getAllTags = () => {
  return http.request<ResponseData<TagDTO[]>>("get", "/admin/home/tag/all");
};

export const getTagDetail = (id: number) => {
  return http.request<TagDTO>("get", `/admin/tag/${id}`);
};

export const addTag = (data: TagRequest) => {
  return http.request<void>("post", "/admin/tag", { data });
};

export const updateTag = (data: TagRequest) => {
  return http.request<void>("put", "/admin/tag", { data });
};

export const deleteTag = (id: number) => {
  return http.request<void>("delete", `/admin/tag/${id}`);
};

export const batchDeleteTag = (ids: number[]) => {
  return http.request<void>("delete", "/admin/tag/batch", { data: ids });
};

// ==================== 平台管理 ====================

export interface PlatformQuery extends BasePageQuery {
  platformName?: string;
  status?: number;
}

export interface PlatformDTO {
  platformId?: number;
  platformName?: string;
  sortOrder?: number;
  status?: number;
  createTime?: Date;
  updateTime?: Date;
}

export interface PlatformRequest {
  platformId?: number;
  platformName: string;
  sortOrder?: number;
  status: number;
}

export const getPlatformList = (data?: PlatformQuery) => {
  return http.request<ResponseData<PageDTO<PlatformDTO>>>("get", "/admin/home/platform/list", {
    params: data
  });
};

export const getAllPlatforms = () => {
  return http.request<PlatformDTO[]>("get", "/admin/home/platform/all");
};

export const getPlatformDetail = (id: number) => {
  return http.request<PlatformDTO>("get", `/admin/home/platform/${id}`);
};

export const addPlatform = (data: PlatformRequest) => {
  return http.request<void>("post", "/admin/home/platform", { data });
};

export const updatePlatform = (data: PlatformRequest) => {
  return http.request<void>("put", `/admin/home/platform/${data.platformId}`, { data });
};

export const deletePlatform = (id: number) => {
  return http.request<void>("delete", `/admin/home/platform/${id}`);
};

export const batchDeletePlatform = (ids: number[]) => {
  return http.request<void>("delete", "/admin/home/platform/batch", { data: ids });
};

// ==================== 达人管理 ====================

export interface InfluencerQuery extends BasePageQuery {
  name?: string;
  platform?: string;
  field?: string;
  status?: number;
}

export interface InfluencerDTO {
  influencerId?: number;
  name?: string;
  platform?: string;
  followers?: string;
  field?: string;
  imageUrl?: string;
  sortOrder?: number;
  status?: number;
  createTime?: Date;
  updateTime?: Date;
}

export interface InfluencerRequest {
  influencerId?: number;
  name: string;
  platform: string;
  followers?: string;
  field?: string;
  imageUrl: string;
  sortOrder?: number;
  status: number;
}

export const getInfluencerList = (data?: InfluencerQuery) => {
  return http.request<ResponseData<PageDTO<InfluencerDTO>>>(
    "get",
    "/admin/home/influencer/list",
    { params: data }
  );
};

export const getInfluencerDetail = (id: number) => {
  return http.request<InfluencerDTO>("get", `/admin/home/influencer/${id}`);
};

export const addInfluencer = (data: InfluencerRequest) => {
  return http.request<void>("post", "/admin/home/influencer", { data });
};

export const updateInfluencer = (data: InfluencerRequest) => {
  return http.request<void>("put", `/admin/home/influencer/${data.influencerId}`, { data });
};

export const deleteInfluencer = (id: number) => {
  return http.request<void>("delete", `/admin/home/influencer/${id}`);
};

export const batchDeleteInfluencer = (ids: number[]) => {
  return http.request<void>("delete", "/admin/home/influencer/batch", {
    data: ids
  });
};

// ==================== 分类管理 ====================

export interface CategoryDTO {
  categoryId?: number;
  categoryName?: string;
  iconName?: string;
  colorClass?: string;
  sortOrder?: number;
  status?: number;
}

export const getAllCategories = () => {
  return http.request<CategoryDTO[]>("get", "/home/categories");
};

export const getAllInfluencers = () => {
  return http.request<ResponseData<InfluencerDTO[]>>("get", "/admin/home/influencer/all");
};

// ==================== 轮播图管理 ====================

export interface BannerQuery extends BasePageQuery {
  title?: string;
  status?: number;
}

export interface BannerDTO {
  bannerId?: number;
  title?: string;
  imageUrl?: string;
  linkUrl?: string;
  sortOrder?: number;
  status?: number;
  createTime?: Date;
  updateTime?: Date;
}

export interface BannerRequest {
  bannerId?: number;
  title: string;
  imageUrl: string;
  linkUrl?: string;
  sortOrder?: number;
  status: number;
}

export const getBannerList = (data?: BannerQuery) => {
  return http.request<ResponseData<PageDTO<BannerDTO>>>("get", "/admin/home/banner/list", {
    params: data
  });
};

export const getBannerDetail = (id: number) => {
  return http.request<ResponseData<BannerDTO>>("get", `/admin/home/banner/${id}`);
};

export const addBanner = (data: BannerRequest) => {
  return http.request<ResponseData<void>>("post", "/admin/home/banner", { data });
};

export const updateBanner = (data: BannerRequest) => {
  return http.request<ResponseData<void>>("put", `/admin/home/banner/${data.bannerId}`, { data });
};

export const deleteBanner = (id: number) => {
  return http.request<ResponseData<void>>("delete", `/admin/home/banner/${id}`);
};

export const batchDeleteBanner = (ids: number[]) => {
  return http.request<ResponseData<void>>("delete", "/admin/home/banner/batch", {
    data: ids
  });
};