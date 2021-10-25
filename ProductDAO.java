/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assHT;

import java.util.ArrayList;


public interface ProductDAO {
     public void insert(Product sv);
    public void update(int viTri, Product sv);
    public void delete(int viTri);
    public Product getItemProduct(int viTri);
    public ArrayList<Product> getArrayProducts();
    public void setArrayProduct(ArrayList<Product> ds);
}
