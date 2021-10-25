/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assHT;

import java.util.ArrayList;



public class QLProduct implements ProductDAO{
    ArrayList<Product> listProduct;
    public QLProduct() {
        this.listProduct = new ArrayList<>();
    }

    @Override
    public void insert(Product sv) {
        this.listProduct.add(sv);
    }

    @Override
    public void update(int viTri, Product sv) {
        this.listProduct.set(viTri, sv);
    }

    @Override
    public void delete(int viTri) {
        this.listProduct.remove(viTri);
    }

    @Override
    public Product getItemProduct(int viTri) { 
        return this.listProduct.get(viTri);
    }

    @Override
    public ArrayList<Product> getArrayProducts() {
        return this.listProduct;
    }

    @Override
    public void setArrayProduct(ArrayList<Product> ds) { 
        this.listProduct = ds;
    }

    
}

