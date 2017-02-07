package com.shashadhardas.earthquakemonitor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shashadhar on 05-02-2017.
 */

public class EarthQuake implements Parcelable {

    private Metadata metadata;
    private List<Feature> features;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public static class Metadata implements Parcelable {
        private long generated;
        private String title;
        private int count;

        public long getGenerated() {
            return generated;
        }

        public void setGenerated(long generated) {
            this.generated = generated;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.generated);
            dest.writeString(this.title);
            dest.writeInt(this.count);
        }

        public Metadata() {
        }

        protected Metadata(Parcel in) {
            this.generated = in.readLong();
            this.title = in.readString();
            this.count = in.readInt();
        }

        public static final Parcelable.Creator<Metadata> CREATOR = new Parcelable.Creator<Metadata>() {
            @Override
            public Metadata createFromParcel(Parcel source) {
                return new Metadata(source);
            }

            @Override
            public Metadata[] newArray(int size) {
                return new Metadata[size];
            }
        };
    }


    public static class Feature implements Parcelable {
        private String type;
        private String id;
        private Properties properties;
        private Geometry geometry;
        private int lastHourFetched;
        private int nextHourToFetch;

        public int getLastHourFetched() {
            return lastHourFetched;
        }

        public void setLastHourFetched(int lastHourFetched) {
            this.lastHourFetched = lastHourFetched;
        }

        public int getNextHourToFetch() {
            return nextHourToFetch;
        }

        public void setNextHourToFetch(int nextHourToFetch) {
            this.nextHourToFetch = nextHourToFetch;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }



        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.type);
            dest.writeString(this.id);
            dest.writeParcelable(this.properties, flags);
            dest.writeParcelable(this.geometry, flags);
        }

        public Feature() {
        }

        protected Feature(Parcel in) {
            this.type = in.readString();
            this.id = in.readString();

            this.properties = in.readParcelable(Properties.class.getClassLoader());
            this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        }

        public static final Parcelable.Creator<Feature> CREATOR = new Parcelable.Creator<Feature>() {
            @Override
            public Feature createFromParcel(Parcel source) {
                return new Feature(source);
            }

            @Override
            public Feature[] newArray(int size) {
                return new Feature[size];
            }
        };
    }

    public static class Properties implements Parcelable {
        private double mag;
        private String title;
        private long time;
        private String url;
        private String place;
        private String detail;


        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public double getMag() {
            return mag;
        }

        public void setMag(double mag) {
            this.mag = mag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public Properties() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.mag);
            dest.writeString(this.title);
            dest.writeLong(this.time);
            dest.writeString(this.url);
            dest.writeString(this.place);
            dest.writeString(this.detail);
        }

        protected Properties(Parcel in) {
            this.mag = in.readDouble();
            this.title = in.readString();
            this.time = in.readLong();
            this.url = in.readString();
            this.place = in.readString();
            this.detail = in.readString();
        }

        public static final Creator<Properties> CREATOR = new Creator<Properties>() {
            @Override
            public Properties createFromParcel(Parcel source) {
                return new Properties(source);
            }

            @Override
            public Properties[] newArray(int size) {
                return new Properties[size];
            }
        };
    }

    public static class Geometry implements Parcelable {
        public ArrayList<Double> coordinates;

        public ArrayList<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(ArrayList<Double> coordinates) {
            this.coordinates = coordinates;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.coordinates);
        }

        public Geometry() {
        }

        protected Geometry(Parcel in) {
            this.coordinates = new ArrayList<Double>();
            in.readList(this.coordinates, Double.class.getClassLoader());
        }

        public static final Parcelable.Creator<Geometry> CREATOR = new Parcelable.Creator<Geometry>() {
            @Override
            public Geometry createFromParcel(Parcel source) {
                return new Geometry(source);
            }

            @Override
            public Geometry[] newArray(int size) {
                return new Geometry[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.metadata, flags);
        dest.writeTypedList(this.features);
    }

    public EarthQuake() {
    }

    protected EarthQuake(Parcel in) {
        this.metadata = in.readParcelable(Metadata.class.getClassLoader());
        this.features = in.createTypedArrayList(Feature.CREATOR);
    }

    public static final Parcelable.Creator<EarthQuake> CREATOR = new Parcelable.Creator<EarthQuake>() {
        @Override
        public EarthQuake createFromParcel(Parcel source) {
            return new EarthQuake(source);
        }

        @Override
        public EarthQuake[] newArray(int size) {
            return new EarthQuake[size];
        }
    };
}
